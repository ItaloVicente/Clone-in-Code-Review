
package com.couchbase.client.java.util;

import org.junit.Test;

import static com.couchbase.client.java.util.AnalyticsIngester.IngestOptions.ingestOptions;

public class AnalyticsIngesterTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowReplaceAndUUID() {
        AnalyticsIngester.ingest(
            null,
            null,
            ingestOptions().ingestMethod(AnalyticsIngester.IngestMethod.REPLACE)
        );
    }

}
