package com.couchbase.client.java.fts.queries;

import static org.junit.Assert.assertEquals;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class RegexpQueryTest {

    @Test
    public void shouldExportRegexpQuery() {
        RegexpQuery query = SearchQuery.regexp("someregexp");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create().put("regexp", "someregexp"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportRegexpQueryWithAllOptions() {
        SearchParams params = SearchParams.build().explain();
        RegexpQuery query = SearchQuery.regexp("someregexp")
            .field("field")
            .boost(1.5);

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("regexp", "someregexp")
                .put("boost", 1.5)
                .put("field", "field"))
            .put("explain", true);
        assertEquals(expected, query.export(params));
    }

}
