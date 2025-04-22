package com.couchbase.client.java.search.queries;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeoBoundingBoxQueryTest {

    @Test
    public void shouldInjectRequiredParams() {
        GeoBoundingBoxQuery query = new GeoBoundingBoxQuery(1.0, 2.0, 3.0, 4.0);
        JsonObject result = JsonObject.create();
        query.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("top_left", JsonArray.from(1.0, 2.0))
            .put("bottom_right", JsonArray.from(3.0, 4.0));
        assertEquals(expected, result);
    }

    @Test
    public void shouldInjectField() {
        GeoBoundingBoxQuery query = new GeoBoundingBoxQuery(1.0, 2.0, 3.0, 4.0);
        query.field("fname");
        JsonObject result = JsonObject.create();
        query.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("top_left", JsonArray.from(1.0, 2.0))
            .put("bottom_right", JsonArray.from(3.0, 4.0))
            .put("field", "fname");
        assertEquals(expected, result);
    }

}
