package com.jss.credentials.manager

import scala.util.Random
import java.util.Arrays
import scala.collection.mutable.Set
import scala.collection.mutable.ArrayBuffer

object PasswordGenerator:
  private val alphabet = "abcdefghijklmnopqrstuvwyz"
  private val numerals = "0123456789"
  private val specialCharacters = "!@#$%&*()=+[.,]_-"

  private val numeralsArray = numerals.toCharArray
  private val alphabetLowercaseArray = alphabet.toCharArray
  private val specialCharactersArray = specialCharacters.toCharArray
  private val alphabetUppercaseArray = alphabet.toCharArray().map(x => x.toUpper)
  
  def generate(length: Int): String = {
    var availableCharactersArrays = ArrayBuffer[Array[Char]](
      numeralsArray,
      alphabetLowercaseArray,
      alphabetUppercaseArray,
      specialCharactersArray
    )
    
    generate(length, availableCharactersArrays)
  }

  def generate(length: Int, specials: Boolean, numbers: Boolean, uppercases: Boolean,  lowercases: Boolean): String = {
    val availableCharactersArrays: ArrayBuffer[Array[Char]] = ArrayBuffer()

    if (specials) availableCharactersArrays += specialCharactersArray
    if (numbers) availableCharactersArrays += numeralsArray
    if (uppercases) availableCharactersArrays += alphabetLowercaseArray
    if (lowercases) availableCharactersArrays += alphabetUppercaseArray
    
    availableCharactersArrays.foreach { array => Arrays.toString(array)}
    
    var password = generate(length, availableCharactersArrays)
    val allCharactersArray: Array[Char] = Array()

    availableCharactersArrays.foreach { array =>
      for i <- array do { allCharactersArray ++ String.valueOf(i) }
    }

    for ch <- password do {
      if (allCharactersArray contains (ch)) {
       password = generate(length, specials, numbers, uppercases, lowercases)
      }
    }

    password
  }

  private def generate(length: Int, availableCharactersArrays: ArrayBuffer[Array[Char]]): String = {
    var password = ""

    for i <- 0 to (length - 1) do
      val selectedArray = availableCharactersArrays(Random.nextInt(availableCharactersArrays.size))
      password += pickRandomItemFromArray(selectedArray)
    
    password
  }

  private def pickRandomItemFromArray(array: Array[Char]): Char = array(Random.nextInt(array.length))
