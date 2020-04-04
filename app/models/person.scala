package models

import play.api.libs.json.{Format, JsArray, JsError, JsObject, JsResult, JsString, JsSuccess, JsValue, Json, OFormat, Reads, Writes}

import scala.collection.immutable._

case class Person(var name:String, var age:Int)
case class Quote(quote:String, author: String)


object Quote {
  implicit val format: OFormat[Quote] = Json.format[Quote]

  implicit val quoteRead: Reads[Seq[Quote]] = Reads.seq(format)

  type Quotes = Seq[Quote]

  def GetQuotes: JsValue = {
    val y: JsValue = Json.arr(
      Json.obj("quote" -> "Make your life a masterpiece, imagine no limitations on what you can be, have or do.", "author" -> "Brian Tracy"),
      Json.obj("quote" -> "We may encounter many defeats but we must not be defeated.", "author" -> "Maya Angelou"),
      Json.obj("quote" -> "I am not a product of my circumstances. I am a product of my decisions.", "author" -> "Stephen Covey"),
      Json.obj("quote" -> "We must let go of the life we have planned, so as to accept the one that is waiting for us.", "author" -> "Joseph Campbell"),
      Json.obj("quote" -> "Believe you can and you're halfway there.", "author" -> "Theodore Roosevelt"),
      Json.obj("quote" -> "We know what we are, but know not what we may be.", "author" -> "William Shakespeare"),
      Json.obj("quote" -> "We can't help everyone, but everyone can help someone.", "author" -> "Ronald Reagan"),
      Json.obj("quote" -> "When you have a dream, you've got to grab it an never let go.", "author" -> "Carol Burnett"),
      Json.obj("quote" -> "Your present circumstances don't determine where you can go; they merely determine where you start.", "author" -> "Nido Quebein"),
      Json.obj("quote" -> "Thinking: the talking of the soul with itself.", "author" -> "Plato")
    )
    y
  }

  def AddToQuotes(dt: Quote): JsResult[Seq[Quote]] = {
    val dataFromJson = this.GetQuotes.validate(quoteRead)

    dataFromJson match {
      case da : JsResult[this.Quotes] => da.get
      case _ => List[Quote]()
    }

    dataFromJson
  }
}

object Person {
   implicit val format: OFormat[Person] = Json.format[Person]
  var persons: List[Person] =  List(
    Person("Joshua", 21),
    Person("Femi", 12),
    Person("David", 23)
  )

  implicit val personWrites = new Writes[Person] {
    override def writes(o: Person): JsValue = {
      Json.obj(
        "name"-> o.name,
        "age"-> o.age
      )
    }
  }

  implicit val personsWrites = new Writes[List[Person]] {
    override def writes(o: List[Person]): JsValue = {
        val persons = o.map { person => Json.obj(
          "name"-> person.name,
          "age" -> person.age
        )}
      JsArray(persons)
    }
  }

  def AddToPerson(p: Person): List[Person] = {
    val perList = List(
      p
    )
    persons ++= perList
    persons
  }

  def findByName(name:String): Option[Person] = {
    val person: Option[Person] = this.persons.find(p => p.name == name)
    person
  }

  def updateByName(pp: Person): List[Person] = {
    this.persons.foreach {
      ( p: Person) => if (p.name == pp.name) {
        p.age = pp.age
      }
    }
    this.persons
  }
}
