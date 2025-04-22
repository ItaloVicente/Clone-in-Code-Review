package com.couchbase.client.java.fts.queries;

import static org.junit.Assert.assertEquals;

import com.couchbase.client.java.document.json.JsonNull;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class BooleanFieldQueryTest {

    @Test
    public void shouldExportBooleanFieldQuery() throws Exception {
        BooleanFieldQuery query = SearchQuery.booleanField(true);

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("bool", true));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportBooleanFieldQueryWithAllOptions() {
        BooleanFieldQuery query = SearchQuery.booleanField(true, SearchParams.build().limit(10))
            .boost(1.5)
            .field("field");

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("bool", true)
                .put("boost", 1.5)
                .put("field", "field"))
            .put("size", 10);
        assertEquals(expected, query.export());
    }
}
