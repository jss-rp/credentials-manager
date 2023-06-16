package com.jss.credentials.manager

import java.util.regex.Pattern
import util.PasswordGenerator

// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class PasswordGeneratorTest extends munit.FunSuite {
  test("Generates a password with length equals to 10 successful") {
    val password = PasswordGenerator.generate(10)
    assertEquals(password.length, 10)
  }

  test("Password must contains at least one special character") {
    val password = PasswordGenerator.generate(length = 10, specials = true, numbers = true, lowercases = true, uppercases = true)
    val pattern = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")

    assert(pattern.matcher(password).find())
  }

  test("Password must not contains special characters") {
    val password = PasswordGenerator.generate(10, specials = false, numbers = true, lowercases = true, uppercases = true)
    val pattern = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")

    assert(!pattern.matcher(password).find())
  }

  test("Password must contains only numbers") {
    val password = PasswordGenerator.generate(10, specials = false, numbers = true, lowercases = false, uppercases = false)
    val pattern = Pattern.compile("[0-9]")

    assert(pattern.matcher(password).find())
  }

  test("Password must contains only letters(camel case)") {
    val password = PasswordGenerator.generate(10, specials = false, numbers = false, lowercases = true, uppercases = true)
    val pattern = Pattern.compile("[a-zA-Z]")
    print(password)
    assert(pattern.matcher(password).find())
  }
}
