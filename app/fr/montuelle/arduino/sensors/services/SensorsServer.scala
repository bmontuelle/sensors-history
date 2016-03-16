package fr.montuelle.arduino.sensors.services

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import com.github.jodersky.flow.SerialSettings
import fr.montuelle.arduino.sensors.actors.Terminal
import play.api.{Configuration, Logger}

trait SensorsServer {

}

@Singleton
class SensorsServerImpl @Inject() (system: ActorSystem, configuration: Configuration, loggerService: LoggerService) extends SensorsServer {
  Logger.debug("Going to start serial actor")

  val port = configuration.getString("serialPortDevice").get
  val bitRate = configuration.getInt("serialPortBitRate").get
  val actorRef = system.actorOf(Terminal.props(port, SerialSettings(bitRate), loggerService),  "serial-terminal-actor")
}