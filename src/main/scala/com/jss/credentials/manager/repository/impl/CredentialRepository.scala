package com.jss.credentials.manager.repository.impl

import com.jss.credentials.manager.repository.Repository
import com.jss.credentials.manager.domain.Credential
import scala.collection.mutable

object CredentialRepository extends Repository[Credential] {
  private val credentials = mutable.Set[Credential]()

  override def save(credential: Credential): Credential = {
    credentials += credential
    credential.clone
  }

  override def findAll(): mutable.Set[Credential] = credentials.clone()
}