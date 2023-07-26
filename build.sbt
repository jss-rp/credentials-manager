val scala3Version = "3.3.0"

lazy val root = project
  .in(file("."))
  .settings(
    fork := true,
    name := "credentials-manager",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.2",
      "ch.qos.logback" % "logback-classic" % "1.4.7",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
      "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
      "org.linguafranca.pwdb" % "KeePassJava2" % "2.1.4",
      "org.scalameta" %% "munit" % "0.7.29" % Test
    )
  )
