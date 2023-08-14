package com.jss.credentials.manager

import com.jss.credentials.manager.config.KeeyPassManager
import com.jss.credentials.manager.ui.{LoginPanel, ManagerPanel}
import com.typesafe.scalalogging.Logger

import java.awt.Color
import javax.swing.border.LineBorder
import scala.swing.*

@main def main(): Unit = {
  val logger = Logger(getClass.getName)

  new MainFrame {
    title = "Credentials Manager"

    contents = LoginPanel { (credentials, panel: LoginPanel) =>
      val keeyPassManager = KeeyPassManager(credentials)

      Option(keeyPassManager.database) match {
        case Some(database) =>
          print("You're logged in")
          val managerPanel = ManagerPanel(keeyPassManager)
          managerPanel.onUpdate = Option({ pack() })
          contents = managerPanel
        case None => panel.contents.foreach { (component: Component) =>
          component match
            case field: TextField => field.peer.setBorder(new LineBorder(Color.red))
            case _ =>
        }
      }
    }

    pack()
    centerOnScreen()
    open()
  }
}
