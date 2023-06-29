package com.jss.credentials.manager.domain


class Credential(
  val username: String,
  val password: String,
  val secret: String,
  val link: String = null
) {

  override def toString: String = s"Credential[username=$username,password=$password,secret=$secret,link=$link]"

  override def clone: Credential = new Credential(username, password, secret, link)
}