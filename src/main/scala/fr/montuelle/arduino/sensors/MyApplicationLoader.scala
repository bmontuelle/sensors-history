import play.api._
import play.api.ApplicationLoader.Context
import play.api.routing.Router

class MyApplicationLoader extends ApplicationLoader {
  def load(context: Context) = {
    new MyComponents(context).application
    Logger.debug("In run")
  }
}

class MyComponents(context: Context) extends BuiltInComponentsFromContext(context) {
  lazy val router = Router.empty
}