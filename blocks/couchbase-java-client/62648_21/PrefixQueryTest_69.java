package com.couchbase.client.java.fts.queries;

import static org.junit.Assert.assertEquals;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class PrefixQueryTest {

    @Test
    public void shouldExportPrefixQuery() {
        PrefixQuery query = SearchQuery.prefix("someterm");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create().put("prefix", "someterm"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportPrefixQueryWithAllOptions() {
        SearchParams params = SearchParams.build().explain();
        PrefixQuery query = SearchQuery.prefix("someterm")
            .field("field")
            .boost(1.5);

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("prefix", "someterm")
                .put("boost", 1.5)
                .put("field", "field"))
            .put("explain", true);
        assertEquals(expected, query.export(params));
    }

}
