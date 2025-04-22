package com.couchbase.client.java.fts.queries;

import static org.junit.Assert.assertEquals;

import com.couchbase.client.java.document.json.JsonNull;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class MatchNoneQueryTest {

    @Test
    public void shouldExportMatchNoneQuery() throws Exception {
        MatchNoneQuery query = SearchQuery.matchNone();

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("match_none", JsonNull.INSTANCE));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportMatchNoneQueryWithAllOptions() {
        MatchNoneQuery query = SearchQuery.matchNone(SearchParams.build().limit(10))
            .boost(1.5);

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("match_none", JsonNull.INSTANCE)
                .put("boost", 1.5))
            .put("size", 10);
        assertEquals(expected, query.export());
    }
}
