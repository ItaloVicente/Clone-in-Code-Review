package com.couchbase.client.java;

import com.couchbase.client.java.analytics.AnalyticsQuery;
import com.couchbase.client.java.analytics.AnalyticsQueryResult;
import com.couchbase.client.java.analytics.AsyncAnalyticsQueryResult;
import com.couchbase.client.java.analytics.AsyncAnalyticsQueryRow;
import org.junit.Test;

import java.util.List;

public class AnalyticsTest {

    @Test
    public void foo() throws Exception {

        System.setProperty("com.couchbase.analyticsEnabled", "true");

        Cluster cluster = CouchbaseCluster.create();
        Bucket bucket = cluster.openBucket();

        AnalyticsQueryResult result = bucket.query(AnalyticsQuery.simple("SELECT 1=1"));
        System.out.println(result);

        Thread.sleep(100000);


    }
}
