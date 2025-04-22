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
        RegexpQuery query = SearchQuery.regexp("someregexp", SearchParams.build().explain())
            .field("field")
            .boost(1.5);

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("regexp", "someregexp")
                .put("boost", 1.5)
                .put("field", "field"))
            .put("explain", true);
        assertEquals(expected, query.export());
    }

}
