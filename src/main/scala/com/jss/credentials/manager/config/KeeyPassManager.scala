package com.jss.credentials.manager.config

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.Logger
import org.linguafranca.pwdb.kdbx.simple.SimpleDatabase
import org.linguafranca.pwdb.kdbx.{KdbxCreds, KdbxStreamFormat}

import java.io.*

object KeeyPassManager {
  private val logger = Logger(getClass.getName)
  private val config = ConfigFactory.load().getConfig("keeypass")
  private val filePath = config.getString("filePath")
  private val secret = config.getString("secret")
  private val file = File(filePath)

  private val credentials = KdbxCreds(secret.getBytes)

  private val database: SimpleDatabase = {
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
      case error: EOFException =>
        logger.warn("Corrupted file! Creating another...")
        file.delete()
        createFile(file)
        createDatabase(FileOutputStream(file))
      case error: IOException => logger.error("Fail on reading KeeyPass file. Error: ", error)

    def createDatabase(outputStream: OutputStream):Unit = {
      result = SimpleDatabase()
      result.save(new KdbxStreamFormat(), credentials, outputStream)
      outputStream.close()
    }

    def createFile(file: File): Unit = {
      file.createNewFile()
      file.setWritable(true)
    }

    result
  }

  def getDatabase: SimpleDatabase = database

  def updateDatabase(): Unit = database.save(new KdbxStreamFormat(), credentials, FileOutputStream(file))
}

