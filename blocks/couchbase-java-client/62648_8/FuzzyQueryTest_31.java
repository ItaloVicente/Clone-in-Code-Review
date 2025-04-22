package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FuzzyQueryTest {

    @Test
    public void shouldExportFuzzyQuery() {
        FuzzyQuery query = SearchQuery.fuzzy("someterm");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create().put("term", "someterm"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportFuzzyQueryWithAllOptions() {
        FuzzyQuery query = SearchQuery.fuzzy("someterm", SearchParams.build().explain())
            .boost(1.5)
            .field("field")
            .prefixLength(23)
            .fuzziness(12);

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("term", "someterm")
                .put("boost", 1.5)
                .put("field", "field")
                .put("prefix_length", 23)
                .put("fuzziness", 12))
            .put("explain", true);
        assertEquals(expected, query.export());
    }

}
