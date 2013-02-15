package controllers

import play.api.mvc._
import models._
import java.util.Date
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import concurrent.{ Await, Future }
import concurrent.duration.Duration
import models.MeetupImporter.getMeetings
import play.api.cache.Cached
import play.api.Play
import play.api.Play.current
import java.text.SimpleDateFormat
import services.MeetingService

// Neo4j
// http://b51498187:fd6b46e88@5701820d8.hosted.neo4j.org:7976

// MongoDB
// mongodb://heroku_app8030104:u37aro46ggr991mvgdmb5on5n9@ds037987.mongolab.com:37987/heroku_app8030104

class Application extends Controller with Formatting {
  def meetingService = MeetingService
  val configuration = Play.current.configuration
  val cacheSeconds = configuration.getInt("cache.seconds").get
  def meetupId = configuration.getString("meetup.meetupId")

  def index = Cached("index", cacheSeconds) {
    Action {
      Async {
        for (
          u <- meetingService.upcoming;
          p <- meetingService.past
        ) yield Ok(views.html.index(u, p reverse))
      }
    }
  }

  def group = Action {
    val groupname = "scala-london"
    Ok(views.html.iframewrapper(groupname))
  }

  def jobs = Action {
    Ok("Jobs")
  }

}

object Application extends Application

trait Formatting {
  val dateFormat = new SimpleDateFormat("yyyy MMM")
  val eventDateFormat = new SimpleDateFormat("dd MMM yyyy, h a")
  def formatDate(d: Date): String = dateFormat.format(d)
  def formatEventTime(d: Date): String = eventDateFormat.format(d)
  def monthName(n: Int): String = {
    val months = new java.text.DateFormatSymbols().getShortMonths
    months(n)
  }
}
