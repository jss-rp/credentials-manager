package com.jss.credentials.manager.ui

import com.jss.credentials.manager.domain.Credential
import com.typesafe.scalalogging.Logger

import scala.swing.*


object CredentialRegisterPanel:
  def apply(
    onSaveButton: (credential: Credential) => Unit
  ): CredentialRegisterPanel =
    new CredentialRegisterPanel(onCredentialRegistration = onSaveButton)

private class CredentialRegisterPanel(
  val orientation: Orientation.Value = Orientation.Vertical,
  val onCredentialRegistration: (credential: Credential) => Unit
) extends BoxPanel(orientation: Orientation.Value):
  private val logger: Logger = Logger(getClass.getName)
  private val usernameField = new TextField(15)
  private val passwordField = new PasswordField(15)
  private val saveButton = new Button("Save")

  contents ++= List(usernameField, passwordField, saveButton)

  saveButton.reactions += {
    case event.ButtonClicked(_) => onCredentialRegistration
      .apply(Credential(
        usernameField.text,
        passwordField.peer.getPassword.mkString
      ))
  }



