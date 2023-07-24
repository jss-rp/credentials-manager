package com.jss.credentials.manager

import com.jss.credentials.manager.cli.TerminalRunner
import com.jss.credentials.manager.domain.Credential
import com.jss.credentials.manager.repository.impl.CredentialRepository
import com.jss.credentials.manager.util.PasswordGenerator
import com.typesafe.config.ConfigFactory
import org.linguafranca.pwdb.Visitor
import org.linguafranca.pwdb.kdbx.{KdbxCreds, KdbxStreamFormat}
import org.linguafranca.pwdb.kdbx.simple.SimpleDatabase
import org.linguafranca.pwdb.kdbx.simple.model.KeePassFile

import java.io.{BufferedInputStream, File, FileInputStream, FileOutputStream, InputStream}
import java.nio.file.{Path, Paths}
import java.sql.*
import java.util.Properties


@main def main(): Unit = {
    TerminalRunner.run(CredentialRepository)
}
