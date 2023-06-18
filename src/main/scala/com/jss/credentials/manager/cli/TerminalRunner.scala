package com.jss.credentials.manager.cli

import java.util.Scanner
import scala.annotation.switch
import com.jss.credentials.manager.domain.Credential
import scala.collection.mutable
import com.jss.credentials.manager.repository.Repository
import com.jss.credentials.manager.util.PasswordGenerator

object TerminalRunner {
  protected val bannerLength = 56
  protected val console = System.console()

  def run(credentialRepository: Repository[Credential]): Unit = {
    printBanner()
    printOptions()

    val selectedOption = console.readLine()

    selectedOption match {
      case "1" => {
        printBanner()
        val credential = requestCredentials()
        credentialRepository.save(credential)

        println("\n ")
        printLoading("Saving")

        run(credentialRepository)
      }
      case "2" => {
        printBanner()
        println

        credentialRepository
          .findAll()
          .foreach { credential => println(credential) }

        println("\nPress any key to quit ...")
        console.readLine
        print("\b\n")
        run(credentialRepository)
      }

      case "3" => println("\nBye bye")
      case _   => run(credentialRepository)
    }
  }

  private def printBanner(): Unit = {
    val title = "CREDENTIALS MANAGER"

    print("\u001b[2J")
    println(s"${"=" * bannerLength}")
    println(s"${(" " * ((bannerLength / 2) - (title.length / 2))) + title}")
    println(s"${"=" * bannerLength}")
  }

  private def printOptions(): Unit = {
    println("[1] Save\t\t[2] Show \t\t[3] Exit")
    print("\n>>> ")
  }

  private def requestCredentials(): Credential = {
    print("\nusername : ")
    val username = console.readLine

    print("\n\nGenerate password? [y/n] ")
    if (evaluateResponse(console)) {
      var password = PasswordGeneratorPrompter(console)

      return new Credential(username, password, null)
    }

    print("password : ")
    val rawPassword = console.readPassword

    val password = rawPassword
      .map { char => mutable.StringBuilder(char) }
      .reduce { (sum, char) => sum.append(char) }
      .toString

    new Credential(username, password, null)
  }

  private def printLoading(subject: String): Unit = {
    val fixedLength = bannerLength - subject.length - 1

    for i <- 0 to fixedLength do {
      print(s"${"\b" * (i + subject.length + 1)}")
      print(s"${subject} ${"." * i}")
      Thread.sleep(23)
    }

    println
  }

  def evaluateResponse(console: java.io.Console): Boolean = {
    console.readLine.toLowerCase match {
      case "y"   => true
      case "yes" => true
      case _     => false
    }
  }

  object PasswordGeneratorPrompter {

    def apply(console: java.io.Console): String = {
      printBanner()
      
      print("\nPassword length: ")
      val length = console.readLine

      print("\nIt must contain special characters? [y/n] ")
      val specials = evaluateResponse(console)

      print("\nIt must contain numbers? [y/n] ")
      val numbers = evaluateResponse(console)

      print("\nIt must contain uppercase characters? [y/n] ")
      val uppercases = evaluateResponse(console)

      var password = PasswordGenerator.generate(length.toInt, specials, numbers, uppercases)

      println(s"\nGenerated password: ${password}")

      print("\nIs that a good password? [y/n] ")
      
      if(evaluateResponse(console)) {
        return password
      } else {
        PasswordGeneratorPrompter(console)
      }
    }
  }
}
