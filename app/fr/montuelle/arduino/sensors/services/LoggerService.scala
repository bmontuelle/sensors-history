package fr.montuelle.arduino.sensors.services

import javax.inject.Inject

import akka.util.ByteString
import org.joda.time.DateTime
import play.api.Logger
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json._

class LoggerService @Inject() (val reactiveMongoApi: ReactiveMongoApi) {
  def collection: JSONCollection = reactiveMongoApi.db.collection[JSONCollection]("sensors-data")

  def log(data: ByteString) = {
    val txtValue = data.utf8String
    Logger.debug(s"receiving message ${txtValue}")
    if (txtValue.contains("|")) {
      val values: Seq[String] = txtValue.split('|').filterNot(_.length == 0)
      Logger.debug(s"data received in text : ${values}")
      collection.insert(Json.obj("temperature" -> values(0), "humidity" -> values(1), "time" -> new DateTime))
    }
  }

}
