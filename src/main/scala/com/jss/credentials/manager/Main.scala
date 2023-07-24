package com.jss.credentials.manager

import com.jss.credentials.manager.cli.TerminalRunner
import com.jss.credentials.manager.repository.impl.CredentialRepository

@main def main(): Unit = {
    TerminalRunner.run(CredentialRepository)
}
