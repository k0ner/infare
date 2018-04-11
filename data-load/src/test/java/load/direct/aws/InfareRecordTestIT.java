package load.direct.aws;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Session;

import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
import static load.direct.aws.TestUtils.createKeyspaceIfNotExists;
import static load.direct.aws.TestUtils.randomSevenDigits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

class InfareRecordTestIT {

    private static final String keyspace = randomSevenDigits();

    private static CassandraSink cassandraSink;

    @BeforeAll
    static void beforeAll() throws IOException, TTransportException {

        EmbeddedCassandraServerHelper.startEmbeddedCassandra(EmbeddedCassandraServerHelper.CASSANDRA_RNDPORT_YML_FILE);
        final Session session = EmbeddedCassandraServerHelper.getSession();

        // set up keyspace
        createKeyspaceIfNotExists(session, keyspace);

        // load data
        final CQLDataLoader testCluster = new CQLDataLoader(session);
        testCluster.load(new ClassPathCQLDataSet("cql/prices.cql", keyspace));

        // create sink
        cassandraSink = new CassandraSink(session, keyspace, "prices", 1);
    }

    @BeforeEach
    void beforeEach() throws Exception {
        EmbeddedCassandraServerHelper.cleanDataEmbeddedCassandra(keyspace);
    }

    @Test
    void shouldBoundAllFieldsToStatement() throws Exception {

        // given
        final InfareRecord infareRecord = InfareRecord.fromLine(lineWithAllFields);

        // when
        final BoundStatement boundStatement = infareRecord.bindToStatement(cassandraSink.insertStatement);

        // then
        assertAll("bounded statement",
                () -> assertThat(boundStatement.getInt("week"), is(Integer.parseInt(observedDateMinAsInfaredate) / 7)),
                () -> assertThat(boundStatement.getInt("min_dte"), is(Integer.parseInt(observedDateMinAsInfaredate))),
                () -> assertThat(boundStatement.getInt("max_dte"), is(Integer.parseInt(observedDateMaxAsInfaredate))),
                () -> assertThat(boundStatement.getInt("weeks_bef"), is(Integer.parseInt(fullWeeksBeforeDeparture))),
                () -> assertThat(boundStatement.getInt("c_id"), is(Integer.parseInt(carrierId))),
                () -> assertThat(boundStatement.getString("class"), is(searchedCabinClass)),
                () -> assertThat(boundStatement.getInt("site"), is(Integer.parseInt(bookingSiteId))),
                () -> assertThat(boundStatement.getInt("s_type"), is(Integer.parseInt(bookingSiteTypeId))),
                () -> assertThat(boundStatement.getBool("one_way"), is(Boolean.parseBoolean(isTripOneWay))),
                () -> assertThat(boundStatement.getInt("orig"), is(Integer.parseInt(tripOriginAirportId))),
                () -> assertThat(boundStatement.getInt("dest"), is(Integer.parseInt(tripDestinationAirportId))),
                () -> assertThat(boundStatement.getInt("min_stay"), is(Integer.parseInt(tripMinStay))),
                () -> assertThat(boundStatement.getDecimal("price_min"), is(BigDecimalFactory.newInstance(tripPriceMin))),
                () -> assertThat(boundStatement.getDecimal("price_max"), is(BigDecimalFactory.newInstance(tripPriceMax))),
                () -> assertThat(boundStatement.getDecimal("price_avg"), is(BigDecimalFactory.newInstance(tripPriceAvg))),
                () -> assertThat(boundStatement.getInt("agg_cnt"), is(Integer.parseInt(aggregationCount))),
                () -> assertThat(boundStatement.getInt("out_dep_dte"), is(Integer.parseInt(outFlightDepartureDateAsInfaredate))),
                () -> assertThat(boundStatement.getInt("out_dep_time"), is(Integer.parseInt(outFlightDepartureTimeAsInfaretime))),
                () -> assertThat(boundStatement.getInt("out_fl_dur"), is(Integer.parseInt(outFlightTimeInMinutes))),
                () -> assertThat(boundStatement.getInt("out_sec_cnt"), is(Integer.parseInt(outSectorCount))),
                () -> assertThat(boundStatement.getInt("out_sec_1"), is(Integer.parseInt(outFlightSector1FlightCodeId))),
                () -> assertThat(boundStatement.getInt("out_sec_2"), is(Integer.parseInt(outFlightSector2FlightCodeId))),
                () -> assertThat(boundStatement.getInt("out_sec_3"), is(Integer.parseInt(outFlightSector3FlightCodeId))),
                () -> assertThat(boundStatement.getInt("hm_dep_dte"), is(Integer.parseInt(homeFlightDepartureDateAsInfaredate))),
                () -> assertThat(boundStatement.getInt("hm_dep_time"), is(Integer.parseInt(homeFlightDepartureTimeAsInfaretime))),
                () -> assertThat(boundStatement.getInt("hm_fl_dur"), is(Integer.parseInt(homeFlightTimeInMinutes))),
                () -> assertThat(boundStatement.getInt("hm_sec_cnt"), is(Integer.parseInt(homeSectorCount))),
                () -> assertThat(boundStatement.getInt("hm_sec_1"), is(Integer.parseInt(homeFlightSector1FlightCodeId))),
                () -> assertThat(boundStatement.getInt("hm_sec_2"), is(Integer.parseInt(homeFlightSector2FlightCodeId))),
                () -> assertThat(boundStatement.getInt("hm_sec_3"), is(Integer.parseInt(homeFlightSector3FlightCodeId))));
    }

    @Test
    void shouldBoundAlsoNullFieldsToStatement() throws Exception {

        // given
        final InfareRecord infareRecord = InfareRecord.fromLine(lineWithOnlyRequiredFields);

        // when
        final BoundStatement boundStatement = infareRecord.bindToStatement(cassandraSink.insertStatement);

        // then
        assertAll("bounded statement",
                () -> assertThat(boundStatement.getInt("week"), is(Integer.parseInt(observedDateMinAsInfaredate) / 7)),
                () -> assertThat(boundStatement.getInt("min_dte"), is(Integer.parseInt(observedDateMinAsInfaredate))),
                () -> assertThat(boundStatement.getInt("max_dte"), is(Integer.parseInt(observedDateMaxAsInfaredate))),
                () -> assertThat(boundStatement.getInt("weeks_bef"), is(Integer.parseInt(fullWeeksBeforeDeparture))),
                () -> assertThat(boundStatement.getInt("c_id"), is(Integer.parseInt(carrierId))),
                () -> assertThat(boundStatement.getString("class"), is(searchedCabinClass)),
                () -> assertThat(boundStatement.getInt("site"), is(Integer.parseInt(bookingSiteId))),
                () -> assertThat(boundStatement.getInt("s_type"), is(Integer.parseInt(bookingSiteTypeId))),
                () -> assertThat(boundStatement.getBool("one_way"), is(Boolean.parseBoolean(isTripOneWay))),
                () -> assertThat(boundStatement.getInt("orig"), is(Integer.parseInt(tripOriginAirportId))),
                () -> assertThat(boundStatement.getInt("dest"), is(Integer.parseInt(tripDestinationAirportId))),
                () -> assertThat(boundStatement.isNull("min_stay"), is(true)),
                () -> assertThat(boundStatement.getDecimal("price_min"), is(BigDecimalFactory.newInstance(tripPriceMin))),
                () -> assertThat(boundStatement.getDecimal("price_max"), is(BigDecimalFactory.newInstance(tripPriceMax))),
                () -> assertThat(boundStatement.getDecimal("price_avg"), is(BigDecimalFactory.newInstance(tripPriceAvg))),
                () -> assertThat(boundStatement.getInt("agg_cnt"), is(Integer.parseInt(aggregationCount))),
                () -> assertThat(boundStatement.getInt("out_dep_dte"), is(Integer.parseInt(outFlightDepartureDateAsInfaredate))),
                () -> assertThat(boundStatement.getInt("out_dep_time"), is(Integer.parseInt(outFlightDepartureTimeAsInfaretime))),
                () -> assertThat(boundStatement.isNull("out_fl_dur"), is(true)),
                () -> assertThat(boundStatement.getInt("out_sec_cnt"), is(Integer.parseInt(outSectorCount))),
                () -> assertThat(boundStatement.getInt("out_sec_1"), is(Integer.parseInt(outFlightSector1FlightCodeId))),
                () -> assertThat(boundStatement.isNull("out_sec_2"), is(true)),
                () -> assertThat(boundStatement.isNull("out_sec_3"), is(true)),
                () -> assertThat(boundStatement.isNull("hm_dep_dte"), is(true)),
                () -> assertThat(boundStatement.isNull("hm_dep_time"), is(true)),
                () -> assertThat(boundStatement.isNull("hm_fl_dur"), is(true)),
                () -> assertThat(boundStatement.getInt("hm_sec_cnt"), is(Integer.parseInt(homeSectorCount))),
                () -> assertThat(boundStatement.isNull("hm_sec_1"), is(true)),
                () -> assertThat(boundStatement.isNull("hm_sec_2"), is(true)),
                () -> assertThat(boundStatement.isNull("hm_sec_3"), is(true)));
    }
}
