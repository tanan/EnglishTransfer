package controllers

import javax.inject._

import play.api.data.Form
import play.api.mvc._
import play.api.data.Forms._

import scala.collection.immutable.ListMap
import scala.io._

/**
  * Created by tanan on 2016/05/04.
  */
class TransferController @Inject() extends Controller {


  val form = Form("url" -> nonEmptyText)

  def transfer = Action { implicit request =>
    val data = form.bindFromRequest
    if (data.hasErrors) {
      Ok("url is not valid.")
    }
    else {
      val html = Source.fromURL(data.get.toString).mkString
      val words = html.split(" ").filter(p => p.matches("^[a-z]{4,}$")).groupBy(f => f).map{case (key, value) => (key, value.length)}
      val sortWords = ListMap(words.toSeq.sortWith(_._2 > _._2):_*)
      val filterWords = sortWords.filter(p => p._2 > 3)
      Ok(views.html.transfer(filterWords))
    }
  }
}

