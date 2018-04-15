#!/bin/bash

files="00000"

offset=0
for file in ${files}; do
    curl https://infare-dev-external-dpcandidate.s3.amazonaws.com/hive_csv_altus_gz/part-${file}.gz | zcat | awk -v offset=$offset '{printf NR+offset"\t"int($1/7)"\t"} {print}' > input-${file}.tsv
    offset=$(($offset+`wc -l input-${file}.tsv | awk '{print $1}'`))
done

if [ ! -f cassandra-loader ]; then
    wget https://github.com/brianmhess/cassandra-loader/releases/download/v0.0.27/cassandra-loader
fi

chmod +x cassandra-loader

for file in `ls input*`; do
    ./cassandra-loader -f $file -host localhost -schema "infare.prices2(id, week, min_dte, max_dte, weeks_bef, c_id, class, site, s_type, one_way, orig, dest, min_stay, price_min, price_max, price_avg, agg_cnt, out_dep_dte, out_dep_time, out_fl_dur, out_sec_cnt, out_sec_1, out_sec_2, out_sec_3, hm_dep_dte, hm_dep_time, hm_fl_dur, hm_sec_cnt, hm_sec_1, hm_sec_2, hm_sec_3)" -delim "\t" -boolStyle 1_0 -batchSize 50 -progressRate 100000 -numRetries 5 -rate 25000 -numFutures 100 -maxErrors 100000
done
