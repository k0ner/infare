import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.util.EntityUtils
import org.json4s._
import org.json4s.jackson.JsonMethods._

object HttpFxService extends FxService {

  lazy val httpClient: CloseableHttpClient = createPool()
  val endpoint = "http://localhost:8080/currency"

  implicit val formats: DefaultFormats.type = DefaultFormats

  override def getPrice(price: BigDecimal): BigDecimal = {
    val response = httpClient.execute(new HttpGet(s"$endpoint?price=$price"))

    (parse(EntityUtils.toString(response.getEntity)) \\ "price").extract[BigDecimal]
  }

  private def createPool(): CloseableHttpClient = {
    val poolingConnManager = new PoolingHttpClientConnectionManager()
    poolingConnManager.setMaxTotal(20)

    HttpClients.custom.setConnectionManager(poolingConnManager).build()
  }
}

