package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def values(val1: Option[String] = Option("test"), val2: Option[Int] = Option(0)) = Action { implicit request =>
    Ok(views.html.index(s"Value 1 is: $val1\nValue 2 is $val2"))
  }
}