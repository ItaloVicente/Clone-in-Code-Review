package com.couchbase.client.java.search.queries;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeoDistanceQueryTest {

    @Test
    public void shouldInjectRequiredParams() {
        GeoDistanceQuery query = new GeoDistanceQuery(1.0, 2.0, "5m");
        JsonObject result = JsonObject.create();
        query.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("location", JsonArray.from(1.0, 2.0))
            .put("distance", "5m");
        assertEquals(expected, result);
    }

    @Test
    public void shouldInjectField() {
        GeoDistanceQuery query = new GeoDistanceQuery(1.0, 2.0, "5m");
        query.field("fname");
        JsonObject result = JsonObject.create();
        query.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("location", JsonArray.from(1.0, 2.0))
            .put("distance", "5m")
            .put("field", "fname");
        assertEquals(expected, result);
    }
}
