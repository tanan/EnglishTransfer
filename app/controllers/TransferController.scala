package controllers

import javax.inject._

import play.api.data.Form
import play.api.mvc._
import play.api.data.Forms._
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
      val wordlist = html.split(" ")
      val s = wordlist.filter(p => wordlist.count(_ == p) == 1)
      val l = s.filter(p => p.matches("^[a-z]{3,}$"))
      Ok(l.mkString("\n"))
    }
  }
}

