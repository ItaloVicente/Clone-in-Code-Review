package com.couchbase.client.java.fts.queries;

import static org.junit.Assert.assertEquals;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonNull;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class MatchAllQueryTest {

    @Test
    public void shouldExportMatchAllQuery() throws Exception {
        MatchAllQuery query = SearchQuery.matchAll();

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("match_all", JsonNull.INSTANCE));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportMatchAllQueryWithAllOptions() {
        MatchAllQuery query = SearchQuery.matchAll(SearchParams.build().limit(10))
            .boost(1.5);

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("match_all", JsonNull.INSTANCE)
                .put("boost", 1.5))
            .put("size", 10);
        assertEquals(expected, query.export());
    }
}
