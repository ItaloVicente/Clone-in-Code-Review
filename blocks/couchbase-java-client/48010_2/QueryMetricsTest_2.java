
package com.couchbase.client.java.query;

import static org.junit.Assert.*;

import com.couchbase.client.java.document.json.JsonObject;
import org.junit.BeforeClass;
import org.junit.Test;

public class QueryMetricsTest {

    @Test
    public void emptyMetricsShouldHaveZeroEverywhere() {
        QueryMetrics metrics = new QueryMetrics(JsonObject.create());

        assertEquals(QueryMetrics.NO_TIME, metrics.elapsedTime());
        assertEquals(QueryMetrics.NO_TIME, metrics.executionTime());
        assertEquals(0, metrics.errorCount());
        assertEquals(0, metrics.mutationCount());
        assertEquals(0, metrics.resultCount());
        assertEquals(0, metrics.warningCount());
        assertEquals(0L, metrics.resultSize());

        assertEquals(0, metrics.asJsonObject().size());
    }

    @Test
    public void wellFormedJsonShouldBeCorrectlyTransformed() {
        JsonObject wellFormed = JsonObject.create()
                  .put("elapsedTime", "123.45ms")
                  .put("executionTime", "200.00ms")
                  .put("resultCount", 1)
                  .put("resultSize", 2L)
                  .put("errorCount", 3)
                  .put("warningCount", 4)
                  .put("mutationCount", 5);
        QueryMetrics metrics = new QueryMetrics(wellFormed);

        assertEquals("123.45ms", metrics.elapsedTime());
        assertEquals("200.00ms", metrics.executionTime());
        assertEquals(1, metrics.resultCount());
        assertEquals(2L, metrics.resultSize());
        assertEquals(3, metrics.errorCount());
        assertEquals(4, metrics.warningCount());
        assertEquals(5, metrics.mutationCount());
    }

    @Test
    public void ensureSourceJsonIsReturnedByAsJson() {
        JsonObject test = JsonObject.create().put("test", "test");
        QueryMetrics metrics = new QueryMetrics(test);

        assertEquals(test, metrics.asJsonObject());
        assertSame(test, metrics.asJsonObject());
    }

    @Test
    public void ensureSourceJsonDoesntBackMetrics() {
        JsonObject partial = JsonObject.create().put("errorCount", 32);
        QueryMetrics metrics = new QueryMetrics(partial);
        partial.put("errorCount", 3);

        assertFalse(metrics.errorCount() == partial.getInt("errorCount"));
        assertEquals(32, metrics.errorCount());
        assertSame(partial, metrics.asJsonObject());
    }
}
