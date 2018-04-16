package rest.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;

@Table("prices2")
public class Trip {

    @PrimaryKeyColumn(name = "week", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Integer week;

    @PrimaryKeyColumn(name = "weeks_bef", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Integer fullWeeksBeforeDeparture;

    @PrimaryKeyColumn(name = "c_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private Integer carrierId;

    @PrimaryKeyColumn(name = "class", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private String bookingClass;

    @PrimaryKeyColumn(name = "site", ordinal = 4, type = PrimaryKeyType.CLUSTERED)
    private Integer bookingSiteId;

    @PrimaryKeyColumn(name = "one_way", ordinal = 5, type = PrimaryKeyType.CLUSTERED)
    private Boolean isTripOneWay;

    @PrimaryKeyColumn(name = "orig", ordinal = 6, type = PrimaryKeyType.CLUSTERED)
    private Integer originAirportId;

    @PrimaryKeyColumn(name = "dest", ordinal = 7, type = PrimaryKeyType.CLUSTERED)
    private Integer destinationAirportId;

    @PrimaryKeyColumn(name = "out_dep_dte", ordinal = 8, type = PrimaryKeyType.CLUSTERED)
    private Integer outFlightDepartureDateAsInfaredate;

    @PrimaryKeyColumn(name = "out_dep_time", ordinal = 9, type = PrimaryKeyType.CLUSTERED)
    private Integer outFlightDepartureTimeAsInfaretime;

    @PrimaryKeyColumn(name = "out_sec_cnt", ordinal = 9, type = PrimaryKeyType.CLUSTERED)
    private Integer outSectorCount;

    @PrimaryKeyColumn(name = "id", ordinal = 10, type = PrimaryKeyType.CLUSTERED)
    private Integer id;

    @Column("price_min")
    private BigDecimal tripPriceMin;

    @Column("price_max")
    private BigDecimal tripPriceMax;

    @Column("price_avg")
    private BigDecimal tripPriceAvg;

    @Column("min_dte")
    private Integer observedDateMinAsInfaredate;

    @Column("max_dte")
    private Integer observedDateMaxAsInfaredate;

    @Column("s_type")
    private Integer bookingSiteTypeId;

    @Column("min_stay")
    private Integer tripMinStay;

    @Column("agg_cnt")
    private Integer aggregationCount;

    @Column("out_fl_dur")
    private Integer outFlightDurationInMinutes;

    @Column("out_sec_1")
    private Integer outFlightSector1FlightCodeId;

    @Column("out_sec_2")
    private Integer outFlightSector2FlightCodeId;

    @Column("out_sec_3")
    private Integer outFlightSector3FlightCodeId;

    @Column("hm_dep_dte")
    private Integer homeFlightDepartureDateAsInfaredate;

    @Column("hm_dep_time")
    private Integer homeFlightDepartureTimeAsInfaretime;

    @Column("hm_fl_dur")
    private Integer homeFlightDurationInMinutes;

    @Column("hm_sec_cnt")
    private Integer homeSectorCount;

    @Column("hm_sec_1")
    private Integer homeFlightSector1FlightCodeId;

    @Column("hm_sec_2")
    private Integer homeFlightSector2FlightCodeId;

    @Column("hm_sec_3")
    private Integer homeFlightSector3FlightCodeId;

    public Trip(Integer week,
                Integer fullWeeksBeforeDeparture,
                Integer carrierId,
                String bookingClass,
                Integer bookingSiteId,
                Boolean isTripOneWay,
                Integer originAirportId,
                Integer destinationAirportId,
                Integer outFlightDepartureDateAsInfaredate,
                Integer outFlightDepartureTimeAsInfaretime,
                Integer outSectorCount,
                BigDecimal tripPriceMin,
                BigDecimal tripPriceMax,
                BigDecimal tripPriceAvg,
                Integer observedDateMinAsInfaredate,
                Integer observedDateMaxAsInfaredate,
                Integer bookingSiteTypeId,
                Integer tripMinStay,
                Integer aggregationCount,
                Integer outFlightDurationInMinutes,
                Integer outFlightSector1FlightCodeId,
                Integer outFlightSector2FlightCodeId,
                Integer outFlightSector3FlightCodeId,
                Integer homeFlightDepartureDateAsInfaredate,
                Integer homeFlightDepartureTimeAsInfaretime,
                Integer homeFlightDurationInMinutes,
                Integer homeSectorCount,
                Integer homeFlightSector1FlightCodeId,
                Integer homeFlightSector2FlightCodeId,
                Integer homeFlightSector3FlightCodeId,
                Integer id) {
        this.week = week;
        this.fullWeeksBeforeDeparture = fullWeeksBeforeDeparture;
        this.carrierId = carrierId;
        this.bookingClass = bookingClass;
        this.bookingSiteId = bookingSiteId;
        this.isTripOneWay = isTripOneWay;
        this.originAirportId = originAirportId;
        this.destinationAirportId = destinationAirportId;
        this.outFlightDepartureDateAsInfaredate = outFlightDepartureDateAsInfaredate;
        this.outFlightDepartureTimeAsInfaretime = outFlightDepartureTimeAsInfaretime;
        this.outSectorCount = outSectorCount;
        this.tripPriceMin = tripPriceMin;
        this.tripPriceMax = tripPriceMax;
        this.tripPriceAvg = tripPriceAvg;
        this.observedDateMinAsInfaredate = observedDateMinAsInfaredate;
        this.observedDateMaxAsInfaredate = observedDateMaxAsInfaredate;
        this.bookingSiteTypeId = bookingSiteTypeId;
        this.tripMinStay = tripMinStay;
        this.aggregationCount = aggregationCount;
        this.outFlightDurationInMinutes = outFlightDurationInMinutes;
        this.outFlightSector1FlightCodeId = outFlightSector1FlightCodeId;
        this.outFlightSector2FlightCodeId = outFlightSector2FlightCodeId;
        this.outFlightSector3FlightCodeId = outFlightSector3FlightCodeId;
        this.homeFlightDepartureDateAsInfaredate = homeFlightDepartureDateAsInfaredate;
        this.homeFlightDepartureTimeAsInfaretime = homeFlightDepartureTimeAsInfaretime;
        this.homeFlightDurationInMinutes = homeFlightDurationInMinutes;
        this.homeSectorCount = homeSectorCount;
        this.homeFlightSector1FlightCodeId = homeFlightSector1FlightCodeId;
        this.homeFlightSector2FlightCodeId = homeFlightSector2FlightCodeId;
        this.homeFlightSector3FlightCodeId = homeFlightSector3FlightCodeId;
        this.id = id;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getFullWeeksBeforeDeparture() {
        return fullWeeksBeforeDeparture;
    }

    public void setFullWeeksBeforeDeparture(Integer fullWeeksBeforeDeparture) {
        this.fullWeeksBeforeDeparture = fullWeeksBeforeDeparture;
    }

    public Integer getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Integer carrierId) {
        this.carrierId = carrierId;
    }

    public String getBookingClass() {
        return bookingClass;
    }

    public void setBookingClass(String bookingClass) {
        this.bookingClass = bookingClass;
    }

    public Integer getBookingSiteId() {
        return bookingSiteId;
    }

    public void setBookingSiteId(Integer bookingSiteId) {
        this.bookingSiteId = bookingSiteId;
    }

    public Boolean getTripOneWay() {
        return isTripOneWay;
    }

    public void setTripOneWay(Boolean tripOneWay) {
        isTripOneWay = tripOneWay;
    }

    public Integer getOriginAirportId() {
        return originAirportId;
    }

    public void setOriginAirportId(Integer originAirportId) {
        this.originAirportId = originAirportId;
    }

    public Integer getDestinationAirportId() {
        return destinationAirportId;
    }

    public void setDestinationAirportId(Integer destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }

    public Integer getOutFlightDepartureDateAsInfaredate() {
        return outFlightDepartureDateAsInfaredate;
    }

    public void setOutFlightDepartureDateAsInfaredate(Integer outFlightDepartureDateAsInfaredate) {
        this.outFlightDepartureDateAsInfaredate = outFlightDepartureDateAsInfaredate;
    }

    public Integer getOutFlightDepartureTimeAsInfaretime() {
        return outFlightDepartureTimeAsInfaretime;
    }

    public void setOutFlightDepartureTimeAsInfaretime(Integer outFlightDepartureTimeAsInfaretime) {
        this.outFlightDepartureTimeAsInfaretime = outFlightDepartureTimeAsInfaretime;
    }

    public Integer getOutSectorCount() {
        return outSectorCount;
    }

    public void setOutSectorCount(Integer outSectorCount) {
        this.outSectorCount = outSectorCount;
    }

    public BigDecimal getTripPriceMin() {
        return tripPriceMin;
    }

    public void setTripPriceMin(BigDecimal tripPriceMin) {
        this.tripPriceMin = tripPriceMin;
    }

    public BigDecimal getTripPriceMax() {
        return tripPriceMax;
    }

    public void setTripPriceMax(BigDecimal tripPriceMax) {
        this.tripPriceMax = tripPriceMax;
    }

    public BigDecimal getTripPriceAvg() {
        return tripPriceAvg;
    }

    public void setTripPriceAvg(BigDecimal tripPriceAvg) {
        this.tripPriceAvg = tripPriceAvg;
    }

    public Integer getObservedDateMinAsInfaredate() {
        return observedDateMinAsInfaredate;
    }

    public void setObservedDateMinAsInfaredate(Integer observedDateMinAsInfaredate) {
        this.observedDateMinAsInfaredate = observedDateMinAsInfaredate;
    }

    public Integer getObservedDateMaxAsInfaredate() {
        return observedDateMaxAsInfaredate;
    }

    public void setObservedDateMaxAsInfaredate(Integer observedDateMaxAsInfaredate) {
        this.observedDateMaxAsInfaredate = observedDateMaxAsInfaredate;
    }

    public Integer getBookingSiteTypeId() {
        return bookingSiteTypeId;
    }

    public void setBookingSiteTypeId(Integer bookingSiteTypeId) {
        this.bookingSiteTypeId = bookingSiteTypeId;
    }

    public Integer getTripMinStay() {
        return tripMinStay;
    }

    public void setTripMinStay(Integer tripMinStay) {
        this.tripMinStay = tripMinStay;
    }

    public Integer getAggregationCount() {
        return aggregationCount;
    }

    public void setAggregationCount(Integer aggregationCount) {
        this.aggregationCount = aggregationCount;
    }

    public Integer getOutFlightDurationInMinutes() {
        return outFlightDurationInMinutes;
    }

    public void setOutFlightDurationInMinutes(Integer outFlightDurationInMinutes) {
        this.outFlightDurationInMinutes = outFlightDurationInMinutes;
    }

    public Integer getOutFlightSector1FlightCodeId() {
        return outFlightSector1FlightCodeId;
    }

    public void setOutFlightSector1FlightCodeId(Integer outFlightSector1FlightCodeId) {
        this.outFlightSector1FlightCodeId = outFlightSector1FlightCodeId;
    }

    public Integer getOutFlightSector2FlightCodeId() {
        return outFlightSector2FlightCodeId;
    }

    public void setOutFlightSector2FlightCodeId(Integer outFlightSector2FlightCodeId) {
        this.outFlightSector2FlightCodeId = outFlightSector2FlightCodeId;
    }

    public Integer getOutFlightSector3FlightCodeId() {
        return outFlightSector3FlightCodeId;
    }

    public void setOutFlightSector3FlightCodeId(Integer outFlightSector3FlightCodeId) {
        this.outFlightSector3FlightCodeId = outFlightSector3FlightCodeId;
    }

    public Integer getHomeFlightDepartureDateAsInfaredate() {
        return homeFlightDepartureDateAsInfaredate;
    }

    public void setHomeFlightDepartureDateAsInfaredate(Integer homeFlightDepartureDateAsInfaredate) {
        this.homeFlightDepartureDateAsInfaredate = homeFlightDepartureDateAsInfaredate;
    }

    public Integer getHomeFlightDepartureTimeAsInfaretime() {
        return homeFlightDepartureTimeAsInfaretime;
    }

    public void setHomeFlightDepartureTimeAsInfaretime(Integer homeFlightDepartureTimeAsInfaretime) {
        this.homeFlightDepartureTimeAsInfaretime = homeFlightDepartureTimeAsInfaretime;
    }

    public Integer getHomeFlightDurationInMinutes() {
        return homeFlightDurationInMinutes;
    }

    public void setHomeFlightDurationInMinutes(Integer homeFlightDurationInMinutes) {
        this.homeFlightDurationInMinutes = homeFlightDurationInMinutes;
    }

    public Integer getHomeSectorCount() {
        return homeSectorCount;
    }

    public void setHomeSectorCount(Integer homeSectorCount) {
        this.homeSectorCount = homeSectorCount;
    }

    public Integer getHomeFlightSector1FlightCodeId() {
        return homeFlightSector1FlightCodeId;
    }

    public void setHomeFlightSector1FlightCodeId(Integer homeFlightSector1FlightCodeId) {
        this.homeFlightSector1FlightCodeId = homeFlightSector1FlightCodeId;
    }

    public Integer getHomeFlightSector2FlightCodeId() {
        return homeFlightSector2FlightCodeId;
    }

    public void setHomeFlightSector2FlightCodeId(Integer homeFlightSector2FlightCodeId) {
        this.homeFlightSector2FlightCodeId = homeFlightSector2FlightCodeId;
    }

    public Integer getHomeFlightSector3FlightCodeId() {
        return homeFlightSector3FlightCodeId;
    }

    public void setHomeFlightSector3FlightCodeId(Integer homeFlightSector3FlightCodeId) {
        this.homeFlightSector3FlightCodeId = homeFlightSector3FlightCodeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

