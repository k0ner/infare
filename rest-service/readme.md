# task 3 - rest service exercise

Rest service was implemented using `spring-boot` and `spring-data` capabilities. The only custom code is related to writing endpoints. There is one endpoint supported:

```
http://localhost:8080/prices
``` 

following filters are supported:

```
?carrierId=
?bookingClass=
?bookingSiteId=
?originAirportId=
?desinationAirportId=
```

only one filter can be chosen at a time.

Additionally pagination is supported by using:

```
?page=X&size=Y
```

The limitation for filtering by fields is that Cassandra has to have indexes set up for these columns.