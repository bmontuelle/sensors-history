name := "sensors-history"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

//resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

resolvers += Resolver.sonatypeRepo("public")

//add resolver to Bintray's jcenter
resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "com.github.jodersky" %% "flow-core" % "2.5.0-RC1",
  "com.github.jodersky" % "flow-native" % "2.5.0-RC1" % "runtime",
  "com.github.jodersky" %% "flow-stream" % "2.5.0-RC1",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.9-play25-SNAPSHOT",
  "net.sigusr" %% "scala-mqtt-client" % "0.6.0"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)
