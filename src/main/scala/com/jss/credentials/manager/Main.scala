package com.jss.credentials.manager

import domain.Credential
import cli.TerminalRunner
import scala.collection.mutable
import repository.impl.CredentialRepository
import util.PasswordGenerator

@main def hello: Unit = TerminalRunner.run(CredentialRepository)
