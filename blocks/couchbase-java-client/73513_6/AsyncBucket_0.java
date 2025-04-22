package com.couchbase.client.java;

import com.couchbase.client.java.analytics.AnalyticsQuery;
import com.couchbase.client.java.analytics.AnalyticsQueryResult;

public class AnalyticsTest {

    public static void main(String... args) throws Exception {

        System.setProperty("com.couchbase.analyticsEnabled", "true");

        Cluster cluster = CouchbaseCluster.create();
        Bucket bucket = cluster.openBucket();

        AnalyticsQueryResult result = bucket.query(AnalyticsQuery.simple("SELECT 1=1;"));
        System.out.println(result);
    }
}
