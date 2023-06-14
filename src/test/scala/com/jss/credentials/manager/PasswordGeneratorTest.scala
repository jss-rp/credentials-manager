package com.jss.credentials.manager
import com.jss.credentials.manager.PasswordGenerator

// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class PasswordGeneratorTest extends munit.FunSuite {
  test("Generate a password with length 10 successful") {
    val password = PasswordGenerator.generate(10)
    assertEquals(password.length, 10)
  }
}
