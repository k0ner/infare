case class TripWithConvertedPrice(week: Integer,
                                  weeksBefore: Integer,
                                  carrier: Integer,
                                  bookingClass: java.lang.String,
                                  bookingSite: Integer,
                                  isOneWay: java.lang.Boolean,
                                  origin: Integer,
                                  destination: Integer,
                                  outboundDepartureDate: Integer,
                                  outboundDepartureTime: Integer,
                                  outboundSectorCount: Integer,
                                  convertedPrice: java.math.BigDecimal)

object TripWithConvertedPrice {
  def apply(week: Int,
            weeksBefore: Int,
            carrier: Int,
            bookingClass: String,
            bookingSite: Int,
            isOneWay: Boolean,
            origin: Int,
            destination: Int,
            outboundDepartureDate: Int,
            outboundDepartureTime: Int,
            outboundSectorCount: Int,
            convertedPrice: BigDecimal): TripWithConvertedPrice =
    new TripWithConvertedPrice(
      int2Integer(week),
      int2Integer(weeksBefore),
      int2Integer(carrier),
      bookingClass.toString,
      int2Integer(bookingSite),
      boolean2Boolean(isOneWay),
      int2Integer(origin),
      int2Integer(destination),
      int2Integer(outboundDepartureDate),
      int2Integer(outboundDepartureTime),
      int2Integer(outboundSectorCount),
      convertedPrice.bigDecimal
    )
}
