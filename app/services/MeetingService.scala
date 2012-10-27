package services
import models.Meeting

trait MeetingService {
  
  def current : Meeting
  def past : Seq[Meeting]
  def future : Seq[Meeting]

}