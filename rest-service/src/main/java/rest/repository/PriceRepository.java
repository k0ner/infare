package rest.repository;

import rest.model.Trip;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public interface PriceRepository extends CassandraRepository<Trip, Integer> {

    @AllowFiltering
    Slice<Trip> findByCarrierId(Integer carrierId, Pageable page);

    @AllowFiltering
    Slice<Trip> findByBookingClass(String bookingClass, Pageable page);

    @AllowFiltering
    Slice<Trip> findByBookingSiteId(Integer bookingSiteId, Pageable page);

    @AllowFiltering
    Slice<Trip> findByOriginAirportId(Integer originationAirportId, Pageable page);

    @AllowFiltering
    Slice<Trip> findByDestinationAirportId(Integer destinationAirportId, Pageable page);
}
