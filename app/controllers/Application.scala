package controllers

import play.api.mvc._
import services.{MockMeetingService, MeetingService}
import models._
import java.util.Date
import scala.concurrent.ExecutionContext.Implicits.global
import concurrent.{Await, Future}
import concurrent.duration.Duration

// case class Event(id: String, time: Long, rsvp: Int, title: String, description: String) {
//   def descriptionHtml = new Html(description)
// }

class Application(meetingService: MeetingService) extends Controller {

  def dummy = Action {implicit request =>
    val myMeeting = Meeting("A dummy meeting", "past meeting stored in Mongo", new Date(), "www.meetup.com")
    val result = Meeting.dao.insert(myMeeting)
    
    Ok("The new ID is " + result.getOrElse("FAILED").toString)
  }
  
//  with securesocial.core.SecureSocial{

  def index = Action { implicit request =>
    Ok(views.html.index(nextTalk ))
  }

  def group = Action { implicit request => 
  	val groupname = "scala-london"

  	Ok (views.html.iframewrapper(groupname))

  }

  def jobs = Action { implicit request =>
    val groupname = "london-scala-jobs"
  	Ok("Jobs")//views.html.iframewrapper(groupname))
  	
  }
  
   def populateMeetings = Action { request =>

      MeetupImporter.pastMeetings.map { response =>
        	val ids = response.map(Meeting.dao.insert(_))
      }
     Ok("")
  }

  def pastTalks =  Meeting.findAll().filterNot( _.name.contains("ojo")).toList.reverse


  def upcomingMeetings: Seq[Meeting] = {
    val f = MeetupImporter.upcomingMeetings
    Await.result( f, Duration("3 seconds")).filterNot( _.name.contains("ojo")).reverse.tail
  }

  def nextTalk: Meeting = {
    val f = MeetupImporter.upcomingMeetings
    Await.result( f, Duration("3 seconds")).filterNot( _.name.contains("ojo")).reverse.head
  }


  def monthName(n:Int):String = {
    val months = new java.text.DateFormatSymbols().getShortMonths
    months(n)
  }

  // def meetupLogin = Action {
  //   val login = "https://secure.meetup.com/oauth2/authorize?client_id=6uaao0sbsmt6u6rj17v4lu5u9&response_type=code&redirect_uri=http://localhost:9000/muauth"
  //   Results.Redirect(login)
  // }


//   def meetupAuthOK(code: String, state:String) = Action {

//   	val url = "https://secure.meetup.com/oauth2/access"
//   	val params = "client_id=6uaao0sbsmt6u6rj17v4lu5u9&client_secret=pvr2o6mai11p054suufrefp1p1&grant_type=authorization_code&redirect_uri=http://localhost:9000/muauth&code=" + code 


//   	Async {
// 	    WS.url(url + "?" + params).post(params).map { response =>
// 	    	println("response " + response.body)
//           // Ok(views.html.index("Login OK " + response.body  ))
//          val errorCode =  ((response.json \ "error").asOpt[String]) 
//          val accessToken =  ((response.json \ "access_token").asOpt[String]) 
//          val expires = ((response.json \ "expires_in").asOpt[Int])
//          accessToken match {
//          	case Some(aToken) =>
// //            getUserName( aToken)


//          	  val name = userDetails( aToken )
// 	          Ok(views.html.index("Login, Hello " + name, name, None  ))
// //	          Ok(views.html.index("Login OK " + accessToken + " time: " + expires  ))
// 	        case None =>  
//   	          Ok(views.html.index("Login Failed " + errorCode, "", None))
//   	      }
// 	    }
//   	} 
//   }

//   def meetupAuthFailed( state:String) = Action {
//     Ok(views.html.index("Login Failed(meetupAuthFailed): " + state, "", None))
//   }

  
//   def userDetails( accessToken:String ) : String = {
//     val cmd = "https://api.meetup.com/2/profiles?access_token=" + accessToken + "&sign=true&member_id=self&group_urlname=london-scala&page=20&oauth_consumer_key=6uaao0sbsmt6u6rj17v4lu5u9"

//     val x = WS.url(cmd).get.map { response =>
// 	    println("userDetails: " + response.body )
//       val results = (response.json \ "results" )
// 	   	val name =  ((results \\ "name")) 
//       name.head.asOpt[String].get
// //		  name.getOrElse("UNKNOWN")
//     }

//     x.await.get

// //  	"unkown"
//   }


//   // def getUserName( aToken:String) : Result = {
//   //     val cmd = "https://api.meetup.com/2/profiles?access_token=" + aToken + "&sign=true&member_id=self&group_urlname=london-scala&page=20&oauth_consumer_key=6uaao0sbsmt6u6rj17v4lu5u9"

//   //     WS.url(cmd).get.map { response1 =>
//   //       println("userDetails: " + response1.body )
//   //       val name =  ((response1.json \ "name").asOpt[String]) 
//   //       Ok(views.html.index("Login, Hello " + name.getOrElse("UNKNOWN")  ))
//   //     }
//   //   }


//   implicit object EventFormat extends Format[Event] {
//     def reads(json: JsValue): Event = Event(
//       (json \ "id").as[String],
//       (json \ "time").as[Long],
//       (json \ "yes_rsvp_count").as[Int],
//       (json \ "name").as[String],
//       (json \ "description").as[String] //      (json \ "friends").asOpt[List[Event]].getOrElse(List())
//       )
//     //unmarshaling to JSValue is covered in the next paragraph
//     def writes(u: Event): JsValue = JsUndefined("TODO")

//   }


}

object Application extends Application(MockMeetingService) {

}
