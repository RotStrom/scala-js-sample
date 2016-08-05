package simple

import akka.actor.ActorSystem
import spray.http.{HttpEntity, MediaTypes, StatusCodes}
import spray.routing.SimpleRoutingApp

import scala.util.Properties

object Server extends SimpleRoutingApp {
  val friends = collection.mutable.ListBuffer(Person("Ross"), Person("Chandler"), Person("Joey"))

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    val port = Properties.envOrElse("PORT", "8080").toInt
    startServer("0.0.0.0", port = port) {
      get {
        pathSingleSlash {
          complete {
            HttpEntity(MediaTypes.`text/html`, Page.skeleton.render)
          }
        } ~
          path("ajax" / "friends") {
            complete {
              upickle.write(friends)
            }
          } ~
          // getting js from resources (e.g. app-fastopt.js)
          getFromResourceDirectory("")
      } ~
      post {
        path("ajax" / "friends") {
          extract(_.request.entity.asString) { e ⇒
            complete {
              friends += upickle.read[Person](e)
              StatusCodes.OK
            }
          }
        }
      }
    }
  }
}