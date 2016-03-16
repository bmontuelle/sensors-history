name := "sensors-history"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

//resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

//resolvers += Resolver.sonatypeRepo("public")

//add resolver to Bintray's jcenter
resolvers += Resolver.jcenterRepo

libraryDependencies += "com.github.jodersky" %% "flow-core" % "2.5.0-RC1"

//(optional) "fat" jar containing native libraries
libraryDependencies += "com.github.jodersky" % "flow-native" % "2.5.0-RC1" % "runtime"

//(optional & experimental) support for Akka streams
libraryDependencies += "com.github.jodersky" %% "flow-stream" % "2.5.0-RC1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
