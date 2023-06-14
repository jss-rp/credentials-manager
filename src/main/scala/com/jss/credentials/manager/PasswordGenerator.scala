package com.jss.credentials.manager

import scala.util.Random

object PasswordGenerator:
  private val alphabet = "abcdefghijklmnopqrstuvwyz"
  private val numerals = "0123456789"
  private val specialCharacters = "!@#$%&*()=+[.,]_-"

  private val numeralsArray = numerals.toCharArray
  private val alphabetLowercaseArray = alphabet.toCharArray
  private val specialCharactersArary = specialCharacters.toCharArray
  private val alphabetUppercaseArray = alphabet.toCharArray().map(x => x.toUpper)
  
  def generate(length: Int): String =
    var password = "" 
    var availableCharactersArrays = Array(
      numeralsArray,
      alphabetLowercaseArray,
      alphabetUppercaseArray,
      specialCharactersArary
    )
    
    for i <- 0 to (length - 1) do
      val selectedArray = availableCharactersArrays(Random.nextInt(availableCharactersArrays.length))
      password += pickRandomItemFromArray(selectedArray)
    
    password

  private def pickRandomItemFromArray(array: Array[Char]): Char = array(Random.nextInt(array.length))
