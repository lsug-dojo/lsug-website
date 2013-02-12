import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._
import java.util.Date

import controllers.Application
import services.MockMeetingService

class ApplicationSpec extends Specification {

  "Application" should {
    
    "format Date with year and month" in new WithApplication {
      val d = new Date(0)
      val application = new Application(MockMeetingService)
      application.formatDate(d) must beEqualTo("1970 Jan")
    }
  }
}
