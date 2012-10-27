import org.specs2.mutable.Specification
import play.api.test._
import play.api.test.Helpers._

/**
 *
 * @author fanta
 */
class ApplicationSpec extends Specification {


  "Application" should {

    "health check on /" in {
      val result = controllers.Application.index(FakeRequest())
      status(result) must equalTo(OK)
    }
  }

}
