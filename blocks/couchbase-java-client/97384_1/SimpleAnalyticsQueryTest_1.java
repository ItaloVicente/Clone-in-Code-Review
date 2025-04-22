package com.couchbase.client.java.analytics;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SimpleAnalyticsQueryTest {

    @Test
    public void shouldInitWithEmptyParams() {
        SimpleAnalyticsQuery query = new SimpleAnalyticsQuery("statement", null);
        assertEquals(AnalyticsParams.build(), query.params());
        assertEquals("statement;", query.statement());
    }

    @Test
    public void shouldUseCustomParams() {
        AnalyticsParams params = AnalyticsParams.build().serverSideTimeout(1, TimeUnit.SECONDS);
        SimpleAnalyticsQuery query = new SimpleAnalyticsQuery("statement", params);
        assertEquals(params, query.params());
        assertEquals("statement;", query.statement());
    }

}
