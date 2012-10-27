package models
import java.util.Date

case class Meeting (
	val name : String,
	val description : String,
    val time: Date,
    val event_url: String
)