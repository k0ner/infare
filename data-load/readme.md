# data load

There are several ways how to load data to Cassandra:

1. `COPY FROM/TO` command - the easiest way - very suitable for not large data sets. It is possible to load one of the given files with average 8k-10k rows per second. It gives 18 minutes per file and corresponds to almost 10h per whole dataset.

2. Custom application - code is present in `load.direct` package. The idea is to stream Gzipped files directly from http, group records in batches and write them in batches to Cassandra cluster. Maximum achieved throughput was similar to `COPY` command.

3. `cassandra-loader` - general purpose bulk loader https://github.com/brianmhess/cassandra-loader/tree/master/src. It has many configuration options but the best set of options that worked for my local cluster can be found [in this file](src/main/resources/cassandra-loader/download-and-load.sh). Achieved throughput is in theory unlimited but with more than 25k per second was causing in erroring out many records. One file on average was loaded in 5 - 6 minutes. Loader hasn't been tested for the whole dataset so I expect that throughput can go down (with more and more records Cassandra will start doing compactions and CPU utilization will go up).

4. Bulk loader - in theory the fastest way for loading huge amount of data to Cassandra. The disadvantage is that CSV needs to be first transformed to into sstables using [CQLSSTableWriter](https://github.com/apache/cassandra/blob/trunk/src/java/org/apache/cassandra/io/sstable/CQLSSTableWriter.java). It requires some custom code (in my case custom code can be found under `load.bulk` package), also by default the class is not thread-safe and only one thread can use one instance. Fortunately several writers can write to the same location and this is the way how I achieved multi-threading.

    Default `CQLSSTableWriter` parameters (especially buffer size of 128MB) did not work for me. I needed to use 32MB at most, 8GB heap nad G1GC. With these parameters I was able to achieve over 40k records per second. Then `sstableloader` command needs to be run separately - I achieved throughput 100k records per second.