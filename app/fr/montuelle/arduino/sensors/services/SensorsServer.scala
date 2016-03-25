package fr.montuelle.arduino.sensors.services

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import com.github.jodersky.flow.SerialSettings
import fr.montuelle.arduino.sensors.actors.{MqttSubscriber, Terminal}
import play.api.{Configuration, Logger}

trait SensorsServer {

}

@Singleton
class SensorsServerImpl @Inject() (system: ActorSystem,
                                   configuration: Configuration,
                                   loggerService: LoggerService,
                                   mqttListenerService: MqttListenerService) extends SensorsServer {
  Logger.debug("Going to start actors")

  val port = configuration.getString("serialPortDevice").get
  val bitRate = configuration.getInt("serialPortBitRate").get
  val mqttBrokerHost = configuration.getString("mqttBrokerHost").get

  //system.actorOf(Terminal.props(port, SerialSettings(bitRate), loggerService),  "serial-terminal-actor")

  system.actorOf(MqttSubscriber.props(mqttBrokerHost, Vector("events/#"), mqttListenerService),  "mqtt-listener-actor")

}