package load.direct.aws;

final class InfareRecordTestData {

    static final String observedDateMinAsInfaredate = "5327";
    static final String observedDateMaxAsInfaredate = "5333";
    static final String fullWeeksBeforeDeparture = "1";
    static final String carrierId = "12087238";
    static final String searchedCabinClass = "E";
    static final String bookingSiteId = "763";
    static final String bookingSiteTypeId = "3";
    static final String isTripOneWay = "0";
    static final String tripOriginAirportId = "2979696";
    static final String tripDestinationAirportId = "13860083";
    static final String tripMinStay = "7";
    static final String tripPriceMin = "26115.46";
    static final String tripPriceMax = "27952.99";
    static final String tripPriceAvg = "27606.850037";
    static final String aggregationCount = "7";
    static final String outFlightDepartureDateAsInfaredate = "5340";
    static final String outFlightDepartureTimeAsInfaretime = "22200";
    static final String outFlightTimeInMinutes = "780";
    static final String outSectorCount = "2";
    static final String outFlightSector1FlightCodeId = "1629021";
    static final String outFlightSector2FlightCodeId = "13312972";
    static final String outFlightSector3FlightCodeId = "13312";
    static final String homeFlightDepartureDateAsInfaredate = "5347";
    static final String homeFlightDepartureTimeAsInfaretime = "29100";
    static final String homeFlightTimeInMinutes = "405";
    static final String homeSectorCount = "2";
    static final String homeFlightSector1FlightCodeId = "1026055";
    static final String homeFlightSector2FlightCodeId = "10856224";
    static final String homeFlightSector3FlightCodeId = "10988";

    static final String lineWithAllFields = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
            observedDateMinAsInfaredate,
            observedDateMaxAsInfaredate,
            fullWeeksBeforeDeparture,
            carrierId,
            searchedCabinClass,
            bookingSiteId,
            bookingSiteTypeId,
            isTripOneWay,
            tripOriginAirportId,
            tripDestinationAirportId,
            tripMinStay,
            tripPriceMin,
            tripPriceMax,
            tripPriceAvg,
            aggregationCount,
            outFlightDepartureDateAsInfaredate,
            outFlightDepartureTimeAsInfaretime,
            outFlightTimeInMinutes,
            outSectorCount,
            outFlightSector1FlightCodeId,
            outFlightSector2FlightCodeId,
            outFlightSector3FlightCodeId,
            homeFlightDepartureDateAsInfaredate,
            homeFlightDepartureTimeAsInfaretime,
            homeFlightTimeInMinutes,
            homeSectorCount,
            homeFlightSector1FlightCodeId,
            homeFlightSector2FlightCodeId,
            homeFlightSector3FlightCodeId);

    static final String lineWithOnlyRequiredFields = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t\t%s\t%s\t%s\t%s\t%s\t%s\t\t%s\t%s\t\t\t\t\t\t%s\t\t\t",
            observedDateMinAsInfaredate,
            observedDateMaxAsInfaredate,
            fullWeeksBeforeDeparture,
            carrierId,
            searchedCabinClass,
            bookingSiteId,
            bookingSiteTypeId,
            isTripOneWay,
            tripOriginAirportId,
            tripDestinationAirportId,
            tripPriceMin,
            tripPriceMax,
            tripPriceAvg,
            aggregationCount,
            outFlightDepartureDateAsInfaredate,
            outFlightDepartureTimeAsInfaretime,
            outSectorCount,
            outFlightSector1FlightCodeId,
            homeSectorCount);

}
