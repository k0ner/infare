import java.util.concurrent.TimeUnit

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.util.EntityUtils
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Success, Try}

object HttpFxService extends FxService {

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future

  implicit val isDefined = retry.Success[Option[BigDecimal]](x => x != null && x.isDefined)

  lazy val httpClient: CloseableHttpClient = createPool()
  val endpoint = "http://localhost:8080/currency"

  implicit val formats: DefaultFormats.type = DefaultFormats

  override def getPrice(price: BigDecimal): Option[BigDecimal] = {
    def doRetry() = {
      retry.Backoff(delay = Duration(100, TimeUnit.MILLISECONDS), max = 5)
        .apply(() => Future {
          val response = httpClient.execute(new HttpGet(s"$endpoint?price=$price"))
          (parse(EntityUtils.toString(response.getEntity)) \\ "price").extractOpt[BigDecimal]
        })
    }

    Try(Await.result(doRetry(), Duration(3, TimeUnit.SECONDS))) match {
      case Success(fx) => fx
      case _ => None
    }
  }

  private def createPool(): CloseableHttpClient = {
    val poolingConnManager = new PoolingHttpClientConnectionManager()
    poolingConnManager.setMaxTotal(20)

    HttpClients.custom.setConnectionManager(poolingConnManager).build()
  }
}

