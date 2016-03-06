package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import javax.inject.Inject
import org.sedis.Pool
import play.api.libs.json._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (sedisPool: Pool) extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def heartbeat(sellerid: String, ts: String) = Action {
    sedisPool.withJedisClient(client => client.set(sellerid, ts))
    val cts: String = sedisPool.withJedisClient(client => client.get(sellerid))
    println(cts)
    Ok(Json.obj("sellerid" -> sellerid, "ts" -> cts))
  }

}
