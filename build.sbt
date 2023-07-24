val scala3Version = "3.3.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "credentials-manager",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.2",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
      "org.linguafranca.pwdb" % "KeePassJava2" % "2.2-SNAPSHOT",
      "org.scalameta" %% "munit" % "0.7.29" % Test),
    resolvers += "oss.sonatype.org-snapshot" at "https://oss.sonatype.org/content/repositories/snapshots"
  )
