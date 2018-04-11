trait FxService {

  def getPrice(price: BigDecimal): BigDecimal

}

object FixedFxService extends FxService {
  override def getPrice(price: BigDecimal): BigDecimal = 2 * price
}
