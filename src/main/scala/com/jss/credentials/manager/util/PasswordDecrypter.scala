package com.jss.credentials.manager.util

import javax.crypto.Cipher

object PasswordDecrypter {
  def apply(cypherText: String, secret: String) = {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
  }
}
