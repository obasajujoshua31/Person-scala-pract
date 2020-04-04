package models

import play.api.libs.json._

case class Location(lat: Double, long: Double)
case class Resident(name: String, age: Int,  role: Option[String])
case class Place(name:String, location: Location, residents: Seq[Resident])

  class data {
    def writeLocation (): Writes[Location] = {
      implicit val locationWrites: Writes[Location] = (location: Location) => Json.obj(
        "lat" -> location.lat,
        "long" -> location.long
      )
      locationWrites
    }

    def writeResident (): Writes[Resident] = {
      implicit val residentWrites : Writes[Resident] = (resident: Resident) => Json.obj(
        "name"-> resident.name,
        "age"-> resident.age,
        "role"-> resident.role
      )
      residentWrites
    }

    val place = Place(
      "Watership Down",
      Location(51.3555, -90.544),
      Seq(
        Resident("Fiver", 4, None),
        Resident("Bigwig", 6, Some("Owsla"))
      )
    )
  }

