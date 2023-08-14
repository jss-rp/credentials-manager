package com.jss.credentials.manager.ui

import com.typesafe.scalalogging.Logger

import scala.swing.*
import scala.swing.BorderPanel.Position

object LoginPanel:
  private val logger = Logger(getClass.getName)
  private val usernameField = new TextField(15)
  private val passwordField = new PasswordField(15)

  def apply(callback: (credentials: String, panel: LoginPanel) => Unit): LoginPanel =
    usernameField.text = ""

    new LoginPanel {
      private val loginPanel = this
      contents += usernameField
      contents += passwordField
      contents += new BorderPanel {
        val button: Button = new Button("Login") {
          reactions += {
            case event.ButtonClicked(_) => callback.apply(credential, loginPanel)
          }
        }

        layout += button -> Position.Center
      }
    }

  def credential: String = s"${usernameField.text}:${passwordField.password.mkString}"


class LoginPanel(val orientation: Orientation.Value = Orientation.Vertical) extends BoxPanel(orientation: Orientation.Value)
