package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json.{JsValue, Json, OFormat, Reads}
import models.{Person, Quote}
import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable._

@Singleton
class ApiController @Inject() (cc: ControllerComponents) extends  AbstractController (cc){
    def index: Action[AnyContent] = Action {
      implicit request : Request[AnyContent] =>  Ok(Quote.GetQuotes)
    }

  def another :Action[AnyContent] = Action {
     implicit request: Request[AnyContent] => Ok(Json.toJson(Person.persons))
  }

  def addQuote(): Action[JsValue] = Action(parse.json) { implicit request =>
    val newPerson: Person = request.body.as[Person]
    val persons  = Person.AddToPerson(newPerson)
    Ok(Json.toJson(persons))
  }

  def getOne(name: String):Action[AnyContent] = Action {
    implicit request =>
      val person = Person.findByName(name)
      if (person.isEmpty) {
        Status(404)
      } else {
        Ok(Json.toJson(person))
      }
  }

  def updateOne(name: String): Action[JsValue] = Action(parse.json) { implicit request =>
      val person = request.body.as[Person]
      person.name = name

      val updated =  Person.updateByName(person)
      Ok(Json.toJson(updated))
  }
}

