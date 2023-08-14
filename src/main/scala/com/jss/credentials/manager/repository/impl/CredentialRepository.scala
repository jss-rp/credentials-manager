package com.jss.credentials.manager.repository.impl

import com.jss.credentials.manager.config.KeeyPassManager
import com.jss.credentials.manager.domain.Credential
import com.jss.credentials.manager.repository.Repository

import scala.collection.mutable

object CredentialRepository:
  def apply(keeyPassManager: KeeyPassManager) =
    new CredentialRepository(keeyPassManager)


private class CredentialRepository(val keeyPassManager: KeeyPassManager) extends Repository[Credential]:
  private val credentials = mutable.Set[Credential]()

  override def save(credential: Credential): Credential =
    val entry = keeyPassManager.database.newEntry()
    entry.setUsername(credential.username)
    entry.setPassword(credential.password)
    keeyPassManager.database.getRootGroup.addEntry(entry)
    keeyPassManager.updateDatabase()
    credential.clone

  override def findAll(): mutable.Set[Credential] =
    val credentialSet = mutable.Set[Credential]()
    val group = keeyPassManager.database.getRootGroup

    group.getEntries.forEach { entry =>
      val credential = Credential(
        entry.getUsername,
        entry.getPassword
      )

      credentialSet += credential
    }

    credentialSet
