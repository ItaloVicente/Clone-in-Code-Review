
package com.couchbase.client.java.util;

import org.junit.Test;

public class AnalyticsIngesterTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowReplaceAndUUID() {
        AnalyticsIngester.ingest(
            null,
            null,
            AnalyticsIngester.IngestOptions.ingestOptions().ingestMethod(AnalyticsIngester.IngestMethod.REPLACE)
        );
    }

}
