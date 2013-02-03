package models


import play.api._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.concurrent.Promise
import play.api.libs.json.JsValue
import java.util.Date


object MeetupImporter {
  
 // julien's key (need to create one for the app)
  val key = "24a4b6977821146284c192b5d30"
  val groupId = "1562942"
    
  def buildEventsRequest(): String = """https://api.meetup.com/2/events?key="""+key+"&group_id="+groupId+"&page=200"
 
  def getAllMeetingsResponse() = WS.url(buildEventsRequest()).get()
  
  def getAllMeetings(): Promise[Seq[Meeting]]= {
    getAllMeetingsResponse().map( response => {
    	    val meetings = (response.json \  "results").asOpt[Seq[JsValue]]
    	    
    	    meetings match {
    	      case Some(seq) => seq.map(parseJsonMeeting(_))
    	      case _ => Nil
    	    }
     })
  }
  
  def parseJsonMeeting(value : JsValue): Meeting = {
    val name = (value \ "name").asOpt[String]
    val description = (value \ "description").asOpt[String]
    val eventUrl = (value \ "event_url").asOpt[String]
    val date = (value \ "time").asOpt[Long]
    
    (name, description, date, eventUrl) match {
      case (Some(n), Some(desc), Some(timestamp), Some(url)) =>  Meeting(n, desc, new Date(timestamp), url)
      case _ => throw new IllegalStateException("Invalid meeting");
    }
   
  }
  


}