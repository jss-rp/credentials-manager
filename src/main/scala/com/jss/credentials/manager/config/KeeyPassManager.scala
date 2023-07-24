package com.jss.credentials.manager.config

import com.typesafe.config.{Config, ConfigFactory}
import org.linguafranca.pwdb.Database
import org.linguafranca.pwdb.kdb.KdbDatabase
import org.linguafranca.pwdb.kdbx.{KdbxCreds, KdbxStreamFormat}
import org.linguafranca.pwdb.kdbx.simple.{SimpleDatabase, SimpleGroup}

import java.io.{File, FileInputStream, FileOutputStream}

object KeeyPassManager {
  private val config = ConfigFactory.load().getConfig("keeypass")
  private val filePath = config.getString("filePath")
  private val secret = config.getString("secret")
  private val file = File(filePath)

  private val credentials = KdbxCreds(secret.getBytes)

  private val database: SimpleDatabase = {
    if(file.exists())
      SimpleDatabase.load(credentials, FileInputStream(file))
    else
      val helper: SimpleDatabase = SimpleDatabase()
      helper.save(new KdbxStreamFormat(), credentials, FileOutputStream(file))
      helper
  }

  def getDatabase: SimpleDatabase = database

  def updateDatabase(): Unit = database.save(new KdbxStreamFormat(), credentials, FileOutputStream(file))

}

