package fr.montuelle.arduino.sensors.services

import javax.inject.Inject

import akka.util.ByteString
import play.api.Logger
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json.collection.JSONCollection


class LoggerService @Inject() (val reactiveMongoApi: ReactiveMongoApi) {
  def collection: JSONCollection = reactiveMongoApi.db.collection[JSONCollection]("sensors-data")

  def log(data: ByteString) = {
    val text = data.utf8String
    Logger.debug(s"data received in text : ${text}")

   // collection.insert()
  }

  def convert(z : ByteString) = z.foldLeft((List() : List[String], ByteString(), 0, 0))((p, b : Byte) => {
    p._3 match {
      case 0 if p._2.nonEmpty => (p._2.utf8String :: p._1, ByteString(), -1, b.toInt)
      case 0 => (p._1, p._2, -1, b.toInt)
      case -1 => (p._1, p._2, (p._4 << 8) + b.toInt, 0)
      case _ => (p._1, p._2 :+ b, p._3 - 1, 0)
    }
  })

}
