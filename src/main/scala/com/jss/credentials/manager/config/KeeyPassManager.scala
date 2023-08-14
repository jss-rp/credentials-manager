package com.jss.credentials.manager.config

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.Logger
import org.linguafranca.pwdb.kdbx.KdbxCreds
import org.linguafranca.pwdb.kdbx.simple.SimpleDatabase

import java.io.*

object KeeyPassManager:
  def apply(credential: String) = new KeeyPassManager(credential)

class KeeyPassManager(val credential: String):
  private val logger = Logger(getClass.getName)
  private val config = ConfigFactory.load().getConfig("keeypass")
  private val filePath = config.getString("filePath")
  private val file = File(filePath)

  val database: SimpleDatabase =
    val credentials = KdbxCreds(credential.getBytes)
    var result: SimpleDatabase = null

    try
      if (file.exists())
        val stream = FileInputStream(file)
        result = SimpleDatabase.load(credentials, FileInputStream(file))
        stream.close()
      else
        createFile(file)
        createDatabase(FileOutputStream(file))
    catch
      case error: IllegalStateException => logger.debug("Username and/or password are incorrects. Error :", error)
      case error: EOFException =>
        logger.warn("Corrupted file! Creating another...")
        file.delete()
        createFile(file)
        createDatabase(FileOutputStream(file))
      case error: IOException => logger.error("Fail on reading KeeyPass file. Error: ", error)

    def createDatabase(outputStream: OutputStream): Unit = {
      result = SimpleDatabase()
      result.save(credentials, outputStream)
      outputStream.close()
    }

    def createFile(file: File): Unit = {
      file.createNewFile()
      file.setWritable(true)
    }

    result


  def updateDatabase(): Unit = database.save(KdbxCreds(credential.getBytes), FileOutputStream(file))

