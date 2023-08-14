package com.jss.credentials.manager.ui

import com.jss.credentials.manager.config.KeeyPassManager
import com.jss.credentials.manager.domain.Credential
import org.linguafranca.pwdb.kdbx.simple.SimpleDatabase

import scala.collection.mutable.ListBuffer
import scala.swing.*

object ManagerPanel:
  def apply(keeyPassManager: KeeyPassManager): ManagerPanel = new ManagerPanel {
    private val managerPanel: ManagerPanel = this
    private val addButton = new Button("Adicionar")
    private val database: SimpleDatabase = keeyPassManager.database
    private var onNewCredential: Option[Credential => Unit] = None

    private val credentials: ListView[String] = new ListView[String] {
      private val raw = ListBuffer[String]()

      database.getRootGroup.getEntries.forEach { entry =>
        raw += s"${entry.getUsername}:${entry.getPassword}"
      }

      listData = raw

      this.repaint()

      onNewCredential = Some({
        credential =>
          val entry = database.newEntry()

          entry.setUsername(credential.username)
          entry.setPassword(credential.password)

          database.getRootGroup.addEntry(entry)

          raw += credential.toString
          listData = raw

          onUpdate match {
            case Some(callback) =>
              this.repaint()
              keeyPassManager.updateDatabase()
            case None =>
          }
      })
    }

    database.getRootGroup.getEntries.forEach { entry =>
      credentials.listData ++ s"${entry.getUsername} -> ${entry.getPassword}"
    }

    private val horizontalPanel = new BoxPanel(Orientation.Horizontal)
    horizontalPanel.contents += new ScrollPane(credentials)
    horizontalPanel.contents += CredentialRegisterPanel(onNewCredential.get)

    contents += horizontalPanel
  }

class ManagerPanel(val orientation: Orientation.Value = Orientation.Vertical) extends BoxPanel(orientation: Orientation.Value):
  var onUpdate: Option[Unit] = None
