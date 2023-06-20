package com.jss.credentials.manager.util

import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import java.security.SecureRandom
import javax.crypto.KeyGenerator
import java.nio.charset.StandardCharsets
import javax.crypto.spec.PBEKeySpec
import javax.crypto.SecretKeyFactory
import java.util.Base64

object PasswordCryptographyHandler {
  private val salt = "fixedSalt&&&"
  private val initializationVectorArray = Array[Byte](0,1,0,1,1,1,1,1,0,0,0,0,0,0,1,1)

  def encrypt(password: String, secret: String): String = {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    
    cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(secret), generateIvParameterSpec())

    val tupa = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8))
    println(new String(tupa, StandardCharsets.UTF_8))
    Base64.getEncoder().encodeToString(tupa)
  }

  def decrypt(cipherText: String, secret: String): String = {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    
    cipher.init(Cipher.DECRYPT_MODE, generateSecretKey(secret), generateIvParameterSpec())
    println(cipherText)
    println(new String(Base64.getDecoder().decode(cipherText), StandardCharsets.UTF_8))
    val bute = cipher.doFinal(Base64.getDecoder().decode(cipherText))
    println(bute.length)
    new String(bute, StandardCharsets.UTF_8)
  }

  private def generateIvParameterSpec(): IvParameterSpec = {
    new IvParameterSpec(initializationVectorArray)
  }

  private def generateSecretKey(secret: String): SecretKey = {
    // val secureRandom = new SecureRandom()
    val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
    // secureRandom.nextBytes(salt)
    
    val spec = new PBEKeySpec(secret.toCharArray, salt.getBytes, 65536, 256)
    
    new SecretKeySpec(secretKeyFactory.generateSecret(spec).getEncoded, "AES")
  }
}