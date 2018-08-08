name := """tweet"""
organization := "com.soen6441"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "org.webjars" %% "webjars-play" % "2.6.2"
libraryDependencies += "org.webjars" % "bootstrap" % "2.3.2"
libraryDependencies += "org.webjars" % "flot" % "0.8.3"
libraryDependencies += "org.webjars" % "requirejs" % "2.2.0"
libraryDependencies ++= Seq(
  ws
)
libraryDependencies += ehcache
libraryDependencies += "org.mockito" % "mockito-core" % "2.10.0" % "test"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.11",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.11" % Test
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

// Javadoc
sources in (Compile, doc) ~= (_ filter (_.getName endsWith ".java"))
