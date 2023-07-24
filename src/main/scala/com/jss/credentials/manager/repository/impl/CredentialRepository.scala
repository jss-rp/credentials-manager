package com.jss.credentials.manager.repository.impl

import com.jss.credentials.manager.config.KeeyPassManager
import com.jss.credentials.manager.repository.Repository
import com.jss.credentials.manager.domain.Credential
import org.linguafranca.pwdb.kdb.KdbDatabase
import org.linguafranca.pwdb.kdbx.simple.SimpleDatabase

import scala.collection.mutable

object CredentialRepository extends Repository[Credential] {
  private val credentials = mutable.Set[Credential]()
  private val database: SimpleDatabase = KeeyPassManager.getDatabase

  override def save(credential: Credential): Credential = {
    val entry = database.newEntry()

    entry.setUsername(credential.username)
    entry.setPassword(credential.password)
    database.getRootGroup.addEntry(entry)
    KeeyPassManager.updateDatabase()
    credential.clone
  }

  override def findAll(): mutable.Set[Credential] = {
    val credentialSet = mutable.Set[Credential]()
    val group = database.getRootGroup

    group.getEntries.forEach { entry =>
      val credential = Credential(
        entry.getUsername,
        entry.getPassword
      )

      credentialSet += credential
    }

    credentialSet
  }
}