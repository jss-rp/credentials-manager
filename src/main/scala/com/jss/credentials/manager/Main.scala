package com.jss.credentials.manager

@main def hello: Unit =
  val password = PasswordGenerator.generate(10)
  println(password)

