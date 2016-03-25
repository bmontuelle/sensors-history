package fr.montuelle.arduino.sensors.services

import javax.inject.Inject

import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json._

/**
  * Created by bmontuelle on 25/03/2016.
  */
class MqttListenerService  @Inject() (val reactiveMongoApi: ReactiveMongoApi) {
  def collection: JSONCollection = reactiveMongoApi.db.collection[JSONCollection]("events-data")

  def log(topic: String, message: String) = {
    collection.insert(Json.obj("topic" -> topic, "message" -> message))
  }
}
