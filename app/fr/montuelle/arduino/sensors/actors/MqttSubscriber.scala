package fr.montuelle.arduino.sensors.actors

import java.net.{InetAddress, InetSocketAddress}

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.github.jodersky.flow.SerialSettings
import com.typesafe.config.ConfigFactory
import fr.montuelle.arduino.sensors.services.{MqttListenerService, LoggerService}
import net.sigusr.mqtt.api._

class MqttSubscriber(host: String, topics: Vector[String], mqttListenerService: MqttListenerService) extends Actor {

  context.actorOf(Manager.props(new InetSocketAddress(InetAddress.getByName(host), 1883))) ! Connect("localSubscriber")

  def receive: Receive = {
    case Connected ⇒
      println("Successfully connected to $host:1883")
      sender() ! Subscribe(topics zip Vector.fill(topics.length + 1) { AtMostOnce }, 1)
      context become ready(sender())
    case ConnectionFailure(reason) ⇒
      println(s"Connection to $host:1883 failed [$reason]")
  }

  def ready(mqttManager: ActorRef): Receive = {
    case Subscribed(vQoS, MessageId(1)) ⇒
      println("Successfully subscribed to topics:")
      println(topics.mkString(" ", ",\n ", ""))
   /* case Message(`stopTopic`, _) ⇒
      mqttManager ! Disconnect
      context become disconnecting*/
    case Message(topic, payload) ⇒
      val message = new String(payload.to[Array], "UTF-8")
      println(s"[$topic] $message")
      mqttListenerService.log(topic, message)
  }

  def disconnecting(): Receive = {
    case Disconnected ⇒
      println("Disconnected from localhost:1883")
    //  LocalSubscriber.shutdown()
  }
}

object MqttSubscriber {

  def props(host: String, topics: Vector[String], mqttListenerService: MqttListenerService)
  = Props(classOf[MqttSubscriber], host, topics, mqttListenerService)
}