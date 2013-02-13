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

// TODO: find a way to inject the MockMeetingService
// or create FakeApplication
class IndexPageSpec extends Specification {

  "The index page" should {

    "render index template" in new WithApplication {
      val html = views.html.index()
      contentType(html) must be equalTo("text/html")
      contentAsString(html) must contain("London")
    }
  }
}
