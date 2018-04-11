package load.direct;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class InfareRecordTest {

    @Test
    void shouldParseLineWithAllFields() throws Exception {

        // given - the test data

        // when
        final InfareRecord infareRecord = InfareRecord.fromLine(InfareRecordTestData.lineWithAllFields);

        // then
        assertAll("infare record",
                () -> assertThat(infareRecord.observedDateMinAsInfaredate, is(Integer.parseInt(InfareRecordTestData.observedDateMinAsInfaredate))),
                () -> assertThat(infareRecord.observedDateMaxAsInfaredate, is(Integer.parseInt(InfareRecordTestData.observedDateMaxAsInfaredate))),
                () -> assertThat(infareRecord.fullWeeksBeforeDeparture, is(Integer.parseInt(InfareRecordTestData.fullWeeksBeforeDeparture))),
                () -> assertThat(infareRecord.carrierId, is(Integer.parseInt(InfareRecordTestData.carrierId))),
                () -> MatcherAssert.assertThat(infareRecord.searchedCabinClass, Matchers.is(InfareRecordTestData.searchedCabinClass)),
                () -> assertThat(infareRecord.bookingSiteId, is(Integer.parseInt(InfareRecordTestData.bookingSiteId))),
                () -> assertThat(infareRecord.bookingSiteTypeId, is(Integer.parseInt(InfareRecordTestData.bookingSiteTypeId))),
                () -> assertThat(infareRecord.isTripOneWay, is(Boolean.parseBoolean(InfareRecordTestData.isTripOneWay))),
                () -> assertThat(infareRecord.tripOriginAirportId, is(Integer.parseInt(InfareRecordTestData.tripOriginAirportId))),
                () -> assertThat(infareRecord.tripDestinationAirportId, is(Integer.parseInt(InfareRecordTestData.tripDestinationAirportId))),
                () -> MatcherAssert.assertThat(infareRecord.tripMinStay, Matchers.is(InfareRecord.optionalInt(InfareRecordTestData.tripMinStay))),
                () -> assertThat(infareRecord.tripPriceMin, is(BigDecimalFactory.newInstance(InfareRecordTestData.tripPriceMin))),
                () -> assertThat(infareRecord.tripPriceMax, is(BigDecimalFactory.newInstance(InfareRecordTestData.tripPriceMax))),
                () -> assertThat(infareRecord.tripPriceAvg, is(BigDecimalFactory.newInstance(InfareRecordTestData.tripPriceAvg))),
                () -> assertThat(infareRecord.aggregationCount, is(Integer.parseInt(InfareRecordTestData.aggregationCount))),
                () -> assertThat(infareRecord.outFlightDepartureDateAsInfaredate, is(Integer.parseInt(InfareRecordTestData.outFlightDepartureDateAsInfaredate))),
                () -> assertThat(infareRecord.outFlightDepartureTimeAsInfaretime, is(Integer.parseInt(InfareRecordTestData.outFlightDepartureTimeAsInfaretime))),
                () -> MatcherAssert.assertThat(infareRecord.outFlightTimeInMinutes, Matchers.is(InfareRecord.optionalInt(InfareRecordTestData.outFlightTimeInMinutes))),
                () -> assertThat(infareRecord.outSectorCount, is(Integer.parseInt(InfareRecordTestData.outSectorCount))),
                () -> assertThat(infareRecord.outFlightSector1FlightCodeId, is(Integer.parseInt(InfareRecordTestData.outFlightSector1FlightCodeId))),
                () -> MatcherAssert.assertThat(infareRecord.outFlightSector2FlightCodeId, Matchers.is(InfareRecord.optionalInt(InfareRecordTestData.outFlightSector2FlightCodeId))),
                () -> MatcherAssert.assertThat(infareRecord.outFlightSector3FlightCodeId, Matchers.is(InfareRecord.optionalInt(InfareRecordTestData.outFlightSector3FlightCodeId))),
                () -> MatcherAssert.assertThat(infareRecord.homeFlightDepartureDateAsInfaredate, Matchers.is(InfareRecord.optionalInt(InfareRecordTestData.homeFlightDepartureDateAsInfaredate))),
                () -> MatcherAssert.assertThat(infareRecord.homeFlightDepartureTimeAsInfaretime, Matchers.is(InfareRecord.optionalInt(InfareRecordTestData.homeFlightDepartureTimeAsInfaretime))),
                () -> MatcherAssert.assertThat(infareRecord.homeFlightTimeInMinutes, Matchers.is(InfareRecord.optionalInt(InfareRecordTestData.homeFlightTimeInMinutes))),
                () -> assertThat(infareRecord.homeSectorCount, is(Integer.parseInt(InfareRecordTestData.homeSectorCount))),
                () -> MatcherAssert.assertThat(infareRecord.homeFlightSector1FlightCodeId, Matchers.is(InfareRecord.optionalInt(InfareRecordTestData.homeFlightSector1FlightCodeId))),
                () -> MatcherAssert.assertThat(infareRecord.homeFlightSector2FlightCodeId, Matchers.is(InfareRecord.optionalInt(InfareRecordTestData.homeFlightSector2FlightCodeId))),
                () -> MatcherAssert.assertThat(infareRecord.homeFlightSector3FlightCodeId, Matchers.is(InfareRecord.optionalInt(InfareRecordTestData.homeFlightSector3FlightCodeId))));
    }

    @Test
    void shouldParseLineWithOnlyRequiredFields() throws Exception {

        // given - the test data

        // when
        final InfareRecord infareRecord = InfareRecord.fromLine(InfareRecordTestData.lineWithOnlyRequiredFields);

        // then
        assertAll("infare record",
                () -> assertThat(infareRecord.observedDateMinAsInfaredate, is(Integer.parseInt(InfareRecordTestData.observedDateMinAsInfaredate))),
                () -> assertThat(infareRecord.observedDateMaxAsInfaredate, is(Integer.parseInt(InfareRecordTestData.observedDateMaxAsInfaredate))),
                () -> assertThat(infareRecord.fullWeeksBeforeDeparture, is(Integer.parseInt(InfareRecordTestData.fullWeeksBeforeDeparture))),
                () -> assertThat(infareRecord.carrierId, is(Integer.parseInt(InfareRecordTestData.carrierId))),
                () -> MatcherAssert.assertThat(infareRecord.searchedCabinClass, Matchers.is(InfareRecordTestData.searchedCabinClass)),
                () -> assertThat(infareRecord.bookingSiteId, is(Integer.parseInt(InfareRecordTestData.bookingSiteId))),
                () -> assertThat(infareRecord.bookingSiteTypeId, is(Integer.parseInt(InfareRecordTestData.bookingSiteTypeId))),
                () -> assertThat(infareRecord.isTripOneWay, is(Boolean.parseBoolean(InfareRecordTestData.isTripOneWay))),
                () -> assertThat(infareRecord.tripOriginAirportId, is(Integer.parseInt(InfareRecordTestData.tripOriginAirportId))),
                () -> assertThat(infareRecord.tripDestinationAirportId, is(Integer.parseInt(InfareRecordTestData.tripDestinationAirportId))),
                () -> assertThat(infareRecord.tripMinStay, is(Optional.empty())),
                () -> assertThat(infareRecord.tripPriceMin, is(BigDecimalFactory.newInstance(InfareRecordTestData.tripPriceMin))),
                () -> assertThat(infareRecord.tripPriceMax, is(BigDecimalFactory.newInstance(InfareRecordTestData.tripPriceMax))),
                () -> assertThat(infareRecord.tripPriceAvg, is(BigDecimalFactory.newInstance(InfareRecordTestData.tripPriceAvg))),
                () -> assertThat(infareRecord.aggregationCount, is(Integer.parseInt(InfareRecordTestData.aggregationCount))),
                () -> assertThat(infareRecord.outFlightDepartureDateAsInfaredate, is(Integer.parseInt(InfareRecordTestData.outFlightDepartureDateAsInfaredate))),
                () -> assertThat(infareRecord.outFlightDepartureTimeAsInfaretime, is(Integer.parseInt(InfareRecordTestData.outFlightDepartureTimeAsInfaretime))),
                () -> assertThat(infareRecord.outFlightTimeInMinutes, is(Optional.empty())),
                () -> assertThat(infareRecord.outSectorCount, is(Integer.parseInt(InfareRecordTestData.outSectorCount))),
                () -> assertThat(infareRecord.outFlightSector1FlightCodeId, is(Integer.parseInt(InfareRecordTestData.outFlightSector1FlightCodeId))),
                () -> assertThat(infareRecord.outFlightSector2FlightCodeId, is(Optional.empty())),
                () -> assertThat(infareRecord.outFlightSector3FlightCodeId, is(Optional.empty())),
                () -> assertThat(infareRecord.homeFlightDepartureDateAsInfaredate, is(Optional.empty())),
                () -> assertThat(infareRecord.homeFlightDepartureTimeAsInfaretime, is(Optional.empty())),
                () -> assertThat(infareRecord.homeFlightTimeInMinutes, is(Optional.empty())),
                () -> assertThat(infareRecord.homeSectorCount, is(Integer.parseInt(InfareRecordTestData.homeSectorCount))),
                () -> assertThat(infareRecord.homeFlightSector1FlightCodeId, is(Optional.empty())),
                () -> assertThat(infareRecord.homeFlightSector2FlightCodeId, is(Optional.empty())),
                () -> assertThat(infareRecord.homeFlightSector3FlightCodeId, is(Optional.empty())));
    }
}
