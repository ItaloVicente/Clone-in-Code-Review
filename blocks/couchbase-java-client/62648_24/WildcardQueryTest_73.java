package com.couchbase.client.java.fts.queries;

import static org.junit.Assert.assertEquals;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class WildcardQueryTest {

    @Test
    public void shouldExportWildcardQuery() {
        WildcardQuery query = SearchQuery.wildcard("some*term?");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create().put("wildcard", "some*term?"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportWildcardQueryWithAllOptions() {
        SearchParams params = SearchParams.build().limit(10);
        WildcardQuery query = SearchQuery.wildcard("some*term?")
            .boost(1.5)
            .field("field");

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("wildcard", "some*term?")
                .put("boost", 1.5)
                .put("field", "field"))
            .put("size", 10);
        assertEquals(expected, query.export(params));
    }

}
