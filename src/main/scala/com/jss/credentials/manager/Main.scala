package com.jss.credentials.manager

import com.jss.credentials.manager.cli.TerminalRunner
import com.jss.credentials.manager.ui.{LoginPanel, MainPanel}

import scala.swing.*

@main def main(): Unit = {
  new MainFrame {
    title = "Credentials Manager"
    
    contents = LoginPanel { (credentials, panel) =>
      println("ok")
    }
    
    pack()
    centerOnScreen()
    open()
  }
}
