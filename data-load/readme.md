# data load

There are several ways how to load data to Cassandra:
1. COPY FROM/TO command - the easiest way - very suitable for not large data sets. It is possible to load one of the given files with average 8k-10k rows per second. It gives 18 minutes per file and corresponds to almost 10h per whole dataset.