package fr.montuelle.arduino.sensors.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}
import akka.io.IO
import akka.util.ByteString
import com.github.jodersky.flow.{Serial, SerialSettings}
import fr.montuelle.arduino.sensors.services.LoggerService

/**
  *  Sample actor representing a simple terminal.
  */
class Terminal (port: String, settings: SerialSettings, loggerService: LoggerService) extends Actor with ActorLogging {
  import context._

  override def preStart() = {
    log.info(s"Requesting manager to open port: ${port}, baud: ${settings.baud}")
    IO(Serial) ! Serial.Open(port, settings)
  }

  def receive: Receive = {

    case Serial.CommandFailed(cmd, reason) =>
      log.error(s"Connection failed, stopping terminal. Reason: ${reason}")
      context stop self

    case Serial.Opened(port) =>
      log.info(s"Port ${port} is now open.")
      context become opened(sender)
      context watch sender // get notified in the event the operator crashes

  }

  def opened(operator: ActorRef): Receive = {

    case Serial.Received(data) => {
     // log.info(s"Received data: " + data)
      loggerService.log(data)
    }

    case Serial.Closed =>
      log.info("Operator closed normally, exiting terminal.")
      context stop self

    case Terminated(`operator`) =>
      log.error("Operator crashed unexpectedly, exiting terminal.")
      context stop self

    case ":q" =>
      operator ! Serial.Close

    case str: String =>
      operator ! Serial.Write(ByteString(str))

  }

}

object Terminal {
  def props(port: String, settings: SerialSettings, loggerService: LoggerService)
    = Props(classOf[Terminal], port, settings, loggerService)

}