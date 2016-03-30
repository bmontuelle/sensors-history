package fr.montuelle.arduino.sensors.controllers

import javax.inject.Inject

import fr.montuelle.arduino.sensors.services.LoggerService
import play.api.libs.EventSource
import play.api.libs.json.{JsNull, JsValue, Json}
import play.api.mvc.{Controller, Action}
import reactivemongo.api.QueryOpts
import play.modules.reactivemongo.json.ImplicitBSONHandlers._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class Climate @Inject() (loggerService: LoggerService)  extends Controller {
  def getStream = Action {
    val enumerate = loggerService.collection.find(Json.obj())
      .sort(Json.obj("timestamp" -> -1))
      .options(QueryOpts(0, 10))
      .cursor[JsValue].enumerate()

    Ok.chunked(enumerate &> EventSource()).as("text/event-stream").withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
  }
}
