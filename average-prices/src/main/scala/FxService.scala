trait FxService {

  def getPrice(price: BigDecimal): Option[BigDecimal]

}

object FixedFxService extends FxService {
  override def getPrice(price: BigDecimal): Option[BigDecimal] = Some(2 * price)
}
