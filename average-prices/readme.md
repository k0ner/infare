# task 2 - consuming rest service

I considered several different ways of implementing this task and I decided that implementing it using Spark and spark-cassandra-connector is the fastest (in terms of implementation) and easiest way.

I decided to use `.spanBy` partition key (`week`) and iterating over partitions. Rest service is consumed in sequential manner but having several Spark executors can easily provide parallel processing. Every call to rest service will be retried by at most 5 times with exponential backoff strategy. If rest service does not return any data, nothing will be written to Cassandra.

Every batch is stored as an unlogged batch statement - it is batch statement in a partition so writes are atomic and isolated.

  With 1 local executor throughput of 6k records per second has been achieved. Gathering metrics in a way I implemented will not work if more executors are used (every executor will report its own metrics). https://github.com/groupon/spark-metrics libary can be used to accumulate metrics from all executors.

```
-- Meters ----------------------------------------------------------------------
rows
             count = 11402152
         mean rate = 6496.90 events/second
     1-minute rate = 4853.23 events/second
     5-minute rate = 6750.49 events/second
    15-minute rate = 5876.42 events/second
    
-- Timers ----------------------------------------------------------------------
batch-time
             count = 42
         mean rate = 0.02 calls/second
     1-minute rate = 0.02 calls/second
     5-minute rate = 0.03 calls/second
    15-minute rate = 0.02 calls/second
               min = 8000.12 milliseconds
               max = 329273.58 milliseconds
              mean = 91935.53 milliseconds
            stddev = 70403.03 milliseconds
            median = 106610.20 milliseconds
              75% <= 106610.20 milliseconds
              95% <= 245927.90 milliseconds
              98% <= 245927.90 milliseconds
              99% <= 245927.90 milliseconds
            99.9% <= 245927.90 milliseconds
```

## Alternative

As an alternative regular Java application could be used - iterating over Cassandra `tokenid`s can provide very fast way of reading data. To parallelize such application several instances could be run with different `tokenid` ranges.