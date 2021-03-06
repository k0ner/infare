package load.domain;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import load.direct.BigDecimalFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class InfareRecord {

    private static final AtomicInteger counter =
            new AtomicInteger(1 + Integer.parseInt(System.getProperty("records.offset", "0")));

    public static final List<String> columns = asList(
            "week", "min_dte", "max_dte", "weeks_bef", "c_id", "class", "site", "s_type", "one_way", "orig", "dest",
            "min_stay", "price_min", "price_max", "price_avg", "agg_cnt", "out_dep_dte", "out_dep_time", "out_fl_dur",
            "out_sec_cnt", "out_sec_1", "out_sec_2", "out_sec_3", "hm_dep_dte", "hm_dep_time", "hm_fl_dur",
            "hm_sec_cnt", "hm_sec_1", "hm_sec_2", "hm_sec_3", "id");

    private static final Pattern tab = Pattern.compile("\\t");

    public final Integer observedDateMinAsInfaredate;
    public final Integer observedDateMaxAsInfaredate;
    public final Integer fullWeeksBeforeDeparture;
    public final Integer carrierId;
    public final String searchedCabinClass;
    public final Integer bookingSiteId;
    public final Integer bookingSiteTypeId;
    public final Boolean isTripOneWay;
    public final Integer tripOriginAirportId;
    public final Integer tripDestinationAirportId;
    public final Optional<Integer> tripMinStay;
    public final BigDecimal tripPriceMin;
    public final BigDecimal tripPriceMax;
    public final BigDecimal tripPriceAvg;
    public final Integer aggregationCount;
    public final Integer outFlightDepartureDateAsInfaredate;
    public final Integer outFlightDepartureTimeAsInfaretime;
    public final Optional<Integer> outFlightTimeInMinutes;
    public final Integer outSectorCount;
    public final Integer outFlightSector1FlightCodeId;
    public final Optional<Integer> outFlightSector2FlightCodeId;
    public final Optional<Integer> outFlightSector3FlightCodeId;
    public final Optional<Integer> homeFlightDepartureDateAsInfaredate;
    public final Optional<Integer> homeFlightDepartureTimeAsInfaretime;
    public final Optional<Integer> homeFlightTimeInMinutes;
    public final Integer homeSectorCount;
    public final Optional<Integer> homeFlightSector1FlightCodeId;
    public final Optional<Integer> homeFlightSector2FlightCodeId;
    public final Optional<Integer> homeFlightSector3FlightCodeId;
    private final Integer id;

    private final Map<String, Supplier<?>> mappings;

    private InfareRecord(Integer observedDateMinAsInfaredate,
                         Integer observedDateMaxAsInfaredate,
                         Integer fullWeeksBeforeDeparture,
                         Integer carrierId,
                         String searchedCabinClass,
                         Integer bookingSiteId,
                         Integer bookingSiteTypeId,
                         Boolean isTripOneWay,
                         Integer tripOriginAirportId,
                         Integer tripDestinationAirportId,
                         Optional<Integer> tripMinStay,
                         BigDecimal tripPriceMin,
                         BigDecimal tripPriceMax,
                         BigDecimal tripPriceAvg,
                         Integer aggregationCount,
                         Integer outFlightDepartureDateAsInfaredate,
                         Integer outFlightDepartureTimeAsInfaretime,
                         Optional<Integer> outFlightTimeInMinutes,
                         Integer outSectorCount,
                         Integer outFlightSector1FlightCodeId,
                         Optional<Integer> outFlightSector2FlightCodeId,
                         Optional<Integer> outFlightSector3FlightCodeId,
                         Optional<Integer> homeFlightDepartureDateAsInfaredate,
                         Optional<Integer> homeFlightDepartureTimeAsInfaretime,
                         Optional<Integer> homeFlightTimeInMinutes,
                         Integer homeSectorCount,
                         Optional<Integer> homeFlightSector1FlightCodeId,
                         Optional<Integer> homeFlightSector2FlightCodeId,
                         Optional<Integer> homeFlightSector3FlightCodeId) {

        this.observedDateMinAsInfaredate = observedDateMinAsInfaredate;
        this.observedDateMaxAsInfaredate = observedDateMaxAsInfaredate;
        this.fullWeeksBeforeDeparture = fullWeeksBeforeDeparture;
        this.carrierId = carrierId;
        this.searchedCabinClass = searchedCabinClass;
        this.bookingSiteId = bookingSiteId;
        this.bookingSiteTypeId = bookingSiteTypeId;
        this.isTripOneWay = isTripOneWay;
        this.tripOriginAirportId = tripOriginAirportId;
        this.tripDestinationAirportId = tripDestinationAirportId;
        this.tripMinStay = tripMinStay;
        this.tripPriceMin = tripPriceMin;
        this.tripPriceMax = tripPriceMax;
        this.tripPriceAvg = tripPriceAvg;
        this.aggregationCount = aggregationCount;
        this.outFlightDepartureDateAsInfaredate = outFlightDepartureDateAsInfaredate;
        this.outFlightDepartureTimeAsInfaretime = outFlightDepartureTimeAsInfaretime;
        this.outFlightTimeInMinutes = outFlightTimeInMinutes;
        this.outSectorCount = outSectorCount;
        this.outFlightSector1FlightCodeId = outFlightSector1FlightCodeId;
        this.outFlightSector2FlightCodeId = outFlightSector2FlightCodeId;
        this.outFlightSector3FlightCodeId = outFlightSector3FlightCodeId;
        this.homeFlightDepartureDateAsInfaredate = homeFlightDepartureDateAsInfaredate;
        this.homeFlightDepartureTimeAsInfaretime = homeFlightDepartureTimeAsInfaretime;
        this.homeFlightTimeInMinutes = homeFlightTimeInMinutes;
        this.homeSectorCount = homeSectorCount;
        this.homeFlightSector1FlightCodeId = homeFlightSector1FlightCodeId;
        this.homeFlightSector2FlightCodeId = homeFlightSector2FlightCodeId;
        this.homeFlightSector3FlightCodeId = homeFlightSector3FlightCodeId;
        this.id = counter.getAndIncrement();

        this.mappings = generateMappings();
    }

    private Map<String, Supplier<?>> generateMappings() {
        final Map<String, Supplier<?>> mappings = new HashMap<>();

        mappings.put("week", () -> this.observedDateMinAsInfaredate / 7);
        mappings.put("min_dte", () -> this.observedDateMinAsInfaredate);
        mappings.put("max_dte", () -> this.observedDateMaxAsInfaredate);
        mappings.put("weeks_bef", () -> this.fullWeeksBeforeDeparture);
        mappings.put("c_id", () -> this.carrierId);
        mappings.put("class", () -> this.searchedCabinClass);
        mappings.put("site", () -> this.bookingSiteId);
        mappings.put("s_type", () -> this.bookingSiteTypeId);
        mappings.put("one_way", () -> this.isTripOneWay);
        mappings.put("orig", () -> this.tripOriginAirportId);
        mappings.put("dest", () -> this.tripDestinationAirportId);
        mappings.put("min_stay", () -> this.tripMinStay.orElse(null));
        mappings.put("price_min", () -> this.tripPriceMin);
        mappings.put("price_max", () -> this.tripPriceMax);
        mappings.put("price_avg", () -> this.tripPriceAvg);
        mappings.put("agg_cnt", () -> this.aggregationCount);
        mappings.put("out_dep_dte", () -> this.outFlightDepartureDateAsInfaredate);
        mappings.put("out_dep_time", () -> this.outFlightDepartureTimeAsInfaretime);
        mappings.put("out_fl_dur", () -> this.outFlightTimeInMinutes.orElse(null));
        mappings.put("out_sec_cnt", () -> this.outSectorCount);
        mappings.put("out_sec_1", () -> this.outFlightSector1FlightCodeId);
        mappings.put("out_sec_2", () -> this.outFlightSector2FlightCodeId.orElse(null));
        mappings.put("out_sec_3", () -> this.outFlightSector3FlightCodeId.orElse(null));
        mappings.put("hm_dep_dte", () -> this.homeFlightDepartureDateAsInfaredate.orElse(null));
        mappings.put("hm_dep_time", () -> this.homeFlightDepartureTimeAsInfaretime.orElse(null));
        mappings.put("hm_fl_dur", () -> this.homeFlightTimeInMinutes.orElse(null));
        mappings.put("hm_sec_cnt", () -> this.homeSectorCount);
        mappings.put("hm_sec_1", () -> this.homeFlightSector1FlightCodeId.orElse(null));
        mappings.put("hm_sec_2", () -> this.homeFlightSector2FlightCodeId.orElse(null));
        mappings.put("hm_sec_3", () -> this.homeFlightSector3FlightCodeId.orElse(null));
        mappings.put("id", () -> this.id);

        return mappings;
    }

    public static InfareRecord fromLine(String line) {
        final String[] args = tab.split(line, 40);

        return new InfareRecord(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1]),
                Integer.parseInt(args[2]),
                Integer.parseInt(args[3]),
                args[4],
                Integer.parseInt(args[5]),
                Integer.parseInt(args[6]),
                Boolean.parseBoolean(args[7]),
                Integer.parseInt(args[8]),
                Integer.parseInt(args[9]),
                optionalInt(args[10]),
                BigDecimalFactory.newInstance(args[11]),
                BigDecimalFactory.newInstance(args[12]),
                BigDecimalFactory.newInstance(args[13]),
                Integer.parseInt(args[14]),
                Integer.parseInt(args[15]),
                Integer.parseInt(args[16]),
                optionalInt(args[17]),
                Integer.parseInt(args[18]),
                Integer.parseInt(args[19]),
                optionalInt(args[20]),
                optionalInt(args[21]),
                optionalInt(args[22]),
                optionalInt(args[23]),
                optionalInt(args[24]),
                Integer.parseInt(args[25]),
                optionalInt(args[26]),
                optionalInt(args[27]),
                optionalInt(args[28]));
    }

    public static Optional<Integer> optionalInt(String arg) {
        return Optional.ofNullable(arg)
                       .filter(isNotBlank())
                       .map(Integer::parseInt);
    }

    private static Predicate<String> isNotBlank() {
        return string -> string != null && !string.isEmpty();

    }

    public BoundStatement bindToStatement(PreparedStatement preparedStatement) {
        final Object[] valuesToBind = columns.stream()
                                             .map(column -> {
                                                 try {
                                                     return mappings.get(column)
                                                                    .get();
                                                 } catch (NullPointerException e) {
                                                     throw new RuntimeException(e);
                                                 }
                                             })
                                             .toArray();
        return preparedStatement.bind(valuesToBind);
    }

    public Map<String, Object> generateValues() {
        return columns.stream()
                      .map(column -> new Pair<>(column,
                                                mappings.get(column)
                                                        .get()))
                      .filter(pair -> pair.right != null)
                      .collect(Collectors.toMap(pair -> pair.left, pair -> pair.right));
    }

    static class Pair<L, R> {
        final L left;
        final R right;

        Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }
    }
}
