#!/bin/bash

files="00000 00001 00002 00003 00004 00005 00006 00007 00008 00009 00010 00011 00012 00013 00014 00015 00016 00017 00018 00019 00020 00021 00022 00023 00024 00025 00026 00027 00028 00029"

for file in ${files}; do
    curl https://infare-dev-external-dpcandidate.s3.amazonaws.com/hive_csv_altus_gz/part-${file}.gz | zcat | awk '{printf int($1/7)"\t"} {print}' > input-${file}.tsv
done

wget https://github.com/brianmhess/cassandra-loader/releases/download/v0.0.27/cassandra-loader

chmod +x cassandra-loader

for file in `ls input*`; do
    ./cassandra-loader -f $file -host localhost -schema "infare.prices(week, min_dte, max_dte, weeks_bef, c_id, class, site, s_type, one_way, orig, dest, min_stay, price_min, price_max, price_avg, agg_cnt, out_dep_dte, out_dep_time, out_fl_dur, out_sec_cnt, out_sec_1, out_sec_2, out_sec_3, hm_dep_dte, hm_dep_time, hm_fl_dur, hm_sec_cnt, hm_sec_1, hm_sec_2, hm_sec_3)" -delim "\t" -boolStyle 1_0 -batchSize 50 -progressRate 100000 -numRetries 5 -rate 25000 -numFutures 100 -maxErrors 100000
done
