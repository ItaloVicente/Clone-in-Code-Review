package com.couchbase.client.java.analytics;

import com.couchbase.client.java.document.json.JsonObject;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class AnalyticsParamsTest {

    @Test
    public void shouldConvertTimeout() {
        AnalyticsParams params = AnalyticsParams.build().serverSideTimeout(1, TimeUnit.SECONDS);
        JsonObject result = JsonObject.create();
        params.injectParams(result);
        assertEquals("1s", result.getString("timeout"));
    }

}
