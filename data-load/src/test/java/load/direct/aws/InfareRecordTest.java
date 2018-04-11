package load.direct.aws;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static load.direct.aws.InfareRecord.optionalInt;
import static load.direct.aws.InfareRecordTestData.aggregationCount;
import static load.direct.aws.InfareRecordTestData.bookingSiteId;
import static load.direct.aws.InfareRecordTestData.bookingSiteTypeId;
import static load.direct.aws.InfareRecordTestData.carrierId;
import static load.direct.aws.InfareRecordTestData.fullWeeksBeforeDeparture;
import static load.direct.aws.InfareRecordTestData.homeFlightDepartureDateAsInfaredate;
import static load.direct.aws.InfareRecordTestData.homeFlightDepartureTimeAsInfaretime;
import static load.direct.aws.InfareRecordTestData.homeFlightSector1FlightCodeId;
import static load.direct.aws.InfareRecordTestData.homeFlightSector2FlightCodeId;
import static load.direct.aws.InfareRecordTestData.homeFlightSector3FlightCodeId;
import static load.direct.aws.InfareRecordTestData.homeFlightTimeInMinutes;
import static load.direct.aws.InfareRecordTestData.homeSectorCount;
import static load.direct.aws.InfareRecordTestData.isTripOneWay;
import static load.direct.aws.InfareRecordTestData.lineWithAllFields;
import static load.direct.aws.InfareRecordTestData.lineWithOnlyRequiredFields;
import static load.direct.aws.InfareRecordTestData.observedDateMaxAsInfaredate;
import static load.direct.aws.InfareRecordTestData.observedDateMinAsInfaredate;
import static load.direct.aws.InfareRecordTestData.outFlightDepartureDateAsInfaredate;
import static load.direct.aws.InfareRecordTestData.outFlightDepartureTimeAsInfaretime;
import static load.direct.aws.InfareRecordTestData.outFlightSector1FlightCodeId;
import static load.direct.aws.InfareRecordTestData.outFlightSector2FlightCodeId;
import static load.direct.aws.InfareRecordTestData.outFlightSector3FlightCodeId;
import static load.direct.aws.InfareRecordTestData.outFlightTimeInMinutes;
import static load.direct.aws.InfareRecordTestData.outSectorCount;
import static load.direct.aws.InfareRecordTestData.searchedCabinClass;
import static load.direct.aws.InfareRecordTestData.tripDestinationAirportId;
import static load.direct.aws.InfareRecordTestData.tripMinStay;
import static load.direct.aws.InfareRecordTestData.tripOriginAirportId;
import static load.direct.aws.InfareRecordTestData.tripPriceAvg;
import static load.direct.aws.InfareRecordTestData.tripPriceMax;
import static load.direct.aws.InfareRecordTestData.tripPriceMin;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class InfareRecordTest {

    @Test
    void shouldParseLineWithAllFields() throws Exception {

        // given - the test data

        // when
        final InfareRecord infareRecord = InfareRecord.fromLine(lineWithAllFields);

        // then
        assertAll("infare record",
                () -> assertThat(infareRecord.observedDateMinAsInfaredate, is(Integer.parseInt(observedDateMinAsInfaredate))),
                () -> assertThat(infareRecord.observedDateMaxAsInfaredate, is(Integer.parseInt(observedDateMaxAsInfaredate))),
                () -> assertThat(infareRecord.fullWeeksBeforeDeparture, is(Integer.parseInt(fullWeeksBeforeDeparture))),
                () -> assertThat(infareRecord.carrierId, is(Integer.parseInt(carrierId))),
                () -> assertThat(infareRecord.searchedCabinClass, is(searchedCabinClass)),
                () -> assertThat(infareRecord.bookingSiteId, is(Integer.parseInt(bookingSiteId))),
                () -> assertThat(infareRecord.bookingSiteTypeId, is(Integer.parseInt(bookingSiteTypeId))),
                () -> assertThat(infareRecord.isTripOneWay, is(Boolean.parseBoolean(isTripOneWay))),
                () -> assertThat(infareRecord.tripOriginAirportId, is(Integer.parseInt(tripOriginAirportId))),
                () -> assertThat(infareRecord.tripDestinationAirportId, is(Integer.parseInt(tripDestinationAirportId))),
                () -> assertThat(infareRecord.tripMinStay, is(optionalInt(tripMinStay))),
                () -> assertThat(infareRecord.tripPriceMin, is(BigDecimalFactory.newInstance(tripPriceMin))),
                () -> assertThat(infareRecord.tripPriceMax, is(BigDecimalFactory.newInstance(tripPriceMax))),
                () -> assertThat(infareRecord.tripPriceAvg, is(BigDecimalFactory.newInstance(tripPriceAvg))),
                () -> assertThat(infareRecord.aggregationCount, is(Integer.parseInt(aggregationCount))),
                () -> assertThat(infareRecord.outFlightDepartureDateAsInfaredate, is(Integer.parseInt(outFlightDepartureDateAsInfaredate))),
                () -> assertThat(infareRecord.outFlightDepartureTimeAsInfaretime, is(Integer.parseInt(outFlightDepartureTimeAsInfaretime))),
                () -> assertThat(infareRecord.outFlightTimeInMinutes, is(optionalInt(outFlightTimeInMinutes))),
                () -> assertThat(infareRecord.outSectorCount, is(Integer.parseInt(outSectorCount))),
                () -> assertThat(infareRecord.outFlightSector1FlightCodeId, is(Integer.parseInt(outFlightSector1FlightCodeId))),
                () -> assertThat(infareRecord.outFlightSector2FlightCodeId, is(optionalInt(outFlightSector2FlightCodeId))),
                () -> assertThat(infareRecord.outFlightSector3FlightCodeId, is(optionalInt(outFlightSector3FlightCodeId))),
                () -> assertThat(infareRecord.homeFlightDepartureDateAsInfaredate, is(optionalInt(homeFlightDepartureDateAsInfaredate))),
                () -> assertThat(infareRecord.homeFlightDepartureTimeAsInfaretime, is(optionalInt(homeFlightDepartureTimeAsInfaretime))),
                () -> assertThat(infareRecord.homeFlightTimeInMinutes, is(optionalInt(homeFlightTimeInMinutes))),
                () -> assertThat(infareRecord.homeSectorCount, is(Integer.parseInt(homeSectorCount))),
                () -> assertThat(infareRecord.homeFlightSector1FlightCodeId, is(optionalInt(homeFlightSector1FlightCodeId))),
                () -> assertThat(infareRecord.homeFlightSector2FlightCodeId, is(optionalInt(homeFlightSector2FlightCodeId))),
                () -> assertThat(infareRecord.homeFlightSector3FlightCodeId, is(optionalInt(homeFlightSector3FlightCodeId))));
    }

    @Test
    void shouldParseLineWithOnlyRequiredFields() throws Exception {

        // given - the test data

        // when
        final InfareRecord infareRecord = InfareRecord.fromLine(lineWithOnlyRequiredFields);

        // then
        assertAll("infare record",
                () -> assertThat(infareRecord.observedDateMinAsInfaredate, is(Integer.parseInt(observedDateMinAsInfaredate))),
                () -> assertThat(infareRecord.observedDateMaxAsInfaredate, is(Integer.parseInt(observedDateMaxAsInfaredate))),
                () -> assertThat(infareRecord.fullWeeksBeforeDeparture, is(Integer.parseInt(fullWeeksBeforeDeparture))),
                () -> assertThat(infareRecord.carrierId, is(Integer.parseInt(carrierId))),
                () -> assertThat(infareRecord.searchedCabinClass, is(searchedCabinClass)),
                () -> assertThat(infareRecord.bookingSiteId, is(Integer.parseInt(bookingSiteId))),
                () -> assertThat(infareRecord.bookingSiteTypeId, is(Integer.parseInt(bookingSiteTypeId))),
                () -> assertThat(infareRecord.isTripOneWay, is(Boolean.parseBoolean(isTripOneWay))),
                () -> assertThat(infareRecord.tripOriginAirportId, is(Integer.parseInt(tripOriginAirportId))),
                () -> assertThat(infareRecord.tripDestinationAirportId, is(Integer.parseInt(tripDestinationAirportId))),
                () -> assertThat(infareRecord.tripMinStay, is(Optional.empty())),
                () -> assertThat(infareRecord.tripPriceMin, is(BigDecimalFactory.newInstance(tripPriceMin))),
                () -> assertThat(infareRecord.tripPriceMax, is(BigDecimalFactory.newInstance(tripPriceMax))),
                () -> assertThat(infareRecord.tripPriceAvg, is(BigDecimalFactory.newInstance(tripPriceAvg))),
                () -> assertThat(infareRecord.aggregationCount, is(Integer.parseInt(aggregationCount))),
                () -> assertThat(infareRecord.outFlightDepartureDateAsInfaredate, is(Integer.parseInt(outFlightDepartureDateAsInfaredate))),
                () -> assertThat(infareRecord.outFlightDepartureTimeAsInfaretime, is(Integer.parseInt(outFlightDepartureTimeAsInfaretime))),
                () -> assertThat(infareRecord.outFlightTimeInMinutes, is(Optional.empty())),
                () -> assertThat(infareRecord.outSectorCount, is(Integer.parseInt(outSectorCount))),
                () -> assertThat(infareRecord.outFlightSector1FlightCodeId, is(Integer.parseInt(outFlightSector1FlightCodeId))),
                () -> assertThat(infareRecord.outFlightSector2FlightCodeId, is(Optional.empty())),
                () -> assertThat(infareRecord.outFlightSector3FlightCodeId, is(Optional.empty())),
                () -> assertThat(infareRecord.homeFlightDepartureDateAsInfaredate, is(Optional.empty())),
                () -> assertThat(infareRecord.homeFlightDepartureTimeAsInfaretime, is(Optional.empty())),
                () -> assertThat(infareRecord.homeFlightTimeInMinutes, is(Optional.empty())),
                () -> assertThat(infareRecord.homeSectorCount, is(Integer.parseInt(homeSectorCount))),
                () -> assertThat(infareRecord.homeFlightSector1FlightCodeId, is(Optional.empty())),
                () -> assertThat(infareRecord.homeFlightSector2FlightCodeId, is(Optional.empty())),
                () -> assertThat(infareRecord.homeFlightSector3FlightCodeId, is(Optional.empty())));
    }
}
