package com.jss.credentials.manager.domain

class Credential(
    val username: String,
    val password: String,
    val link: String = null):

  override def toString: String = s"Credential[username=${username},password=${password},link=${link}]"
  override def clone: Credential = new Credential(username, password, link)
