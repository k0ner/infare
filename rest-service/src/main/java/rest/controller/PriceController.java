package rest.controller;

import rest.model.Trip;
import rest.repository.PriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@RestController
public class PriceController {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceController(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @RequestMapping("/prices")
    @ResponseBody
    public List<Trip> findByCarrierId(Pageable pageable) {

        return getPage(pageable, priceRepository::findAll);
    }

    @RequestMapping(value = "/prices", params = {"carrierId"})
    @ResponseBody
    public List<Trip> findByCarrierId(@RequestParam Integer carrierId, Pageable pageable) {
        Function<Pageable, Slice<Trip>> findFunction =
                pageRequest -> priceRepository.findByCarrierId(carrierId, pageRequest);

        return getPage(pageable, findFunction);
    }

    @RequestMapping(value = "/prices", params = {"bookingClass"})
    @ResponseBody
    public List<Trip> findByBookingClass(@RequestParam String bookingClass, Pageable pageable) {

        Function<Pageable, Slice<Trip>> findFunction =
                pageRequest -> priceRepository.findByBookingClass(bookingClass, pageRequest);

        return getPage(pageable, findFunction);
    }

    @RequestMapping(value = "/prices", params = {"bookingSiteId"})
    @ResponseBody
    public List<Trip> findByBookingSiteId(@RequestParam Integer bookingSiteId, Pageable pageable) {

        Function<Pageable, Slice<Trip>> findFunction =
                pageRequest -> priceRepository.findByBookingSiteId(bookingSiteId, pageRequest);

        return getPage(pageable, findFunction);
    }

    @RequestMapping(value = "/prices", params = {"originAirportId"})
    @ResponseBody
    public List<Trip> findByOriginAirportId(@RequestParam Integer originAirportId, Pageable pageable) {

        Function<Pageable, Slice<Trip>> findFunction =
                pageRequest -> priceRepository.findByOriginAirportId(originAirportId, pageRequest);

        return getPage(pageable, findFunction);
    }

    @RequestMapping(value = "/prices", params = {"destinationAirportId"})
    @ResponseBody
    public List<Trip> findByDestinationAirportId(@RequestParam Integer destinationAirportId, Pageable pageable) {

        Function<Pageable, Slice<Trip>> findFunction =
                pageRequest -> priceRepository.findByDestinationAirportId(destinationAirportId, pageRequest);

        return getPage(pageable, findFunction);
    }

    private <T> List<T> getPage(Pageable pageable,
                                Function<Pageable, Slice<T>> getSingleSlice) {
        final int pageNumber = pageable.getPageNumber();
        Pageable pageableRequest = CassandraPageRequest.of(0, pageable.getPageSize(), pageable.getSort());

        int counter = 0;
        Slice<T> slice = null;
        while (counter <= pageNumber) {
            slice = getSingleSlice.apply(pageableRequest);
            pageableRequest = slice.nextPageable();
            counter++;
        }

        return slice == null ? Collections.emptyList() : slice.getContent();
    }

}
