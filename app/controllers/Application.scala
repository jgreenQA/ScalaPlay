package controllers

import play.api.mvc._

class Application extends Controller {

  def index = Action { implicit request =>
    val cookieExists = request.cookies.size <= 1

    if (!cookieExists) {
      try {
        Ok(views.html.index(
          s"Your new application is ready." +
            s"\nCookie Name: ${request.cookies.get("foxes").get.name}" +
            s"\nCookie Value: ${request.cookies.get("foxes").get.value}" +
            s"\nSession Name: ${request.session.get("session").getOrElse("no session")}" +
            s"\nFlash Name: ${request.flash.get("flash").getOrElse("no flash")}")
        )
      } catch {
        case e: Exception => {
          Ok(views.html.index(
          s"Your new application is ready." +
            s"\nCookie Name: ${request.cookies.get("maryland").get.name}" +
            s"\nCookie Value: ${request.cookies.get("maryland").get.value}" +
            s"\nSession Name: ${request.session.get("session").getOrElse("no session")}" +
            s"\nFlash Name: ${request.flash.get("flash").getOrElse("no flash")}")
        )
        }
      }

    } else {
      Ok(views.html.index("Cookie not found"))
        .withCookies(Cookie("maryland", "chocolate_chip"))
        //.withCookies(request.cookies.apply("maryland").copy(name = "foxes"))
        .withSession(request.session + ("session" -> "active"))
        .flashing("flash" -> "active")
    }
  }

  def values(value: Option[Int]) = Action {
    Ok(views.html.index(s"Value is: $value"))
  }

  def reverse = Action {
    Redirect(routes.Application.values(Some(1)))
  }

  def amazingMethod = TODO

  def removeData = Action {
    Redirect(routes.Application.index())
      .discardingCookies(DiscardingCookie("maryland"))
      .discardingCookies(DiscardingCookie("foxes"))
      .withNewSession
  }

  def renameCookie = Action { implicit request =>
    Redirect(routes.Application.index())
      .withCookies(Cookie("foxes", request.cookies.get("maryland").get.value))
      .discardingCookies(DiscardingCookie("maryland"))
  }
}