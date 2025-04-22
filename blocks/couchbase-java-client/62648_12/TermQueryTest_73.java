package com.couchbase.client.java.fts.queries;

import static org.junit.Assert.assertEquals;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class TermQueryTest {

    @Test
    public void shouldExportTermQuery() {
        TermQuery query = SearchQuery.term("salty");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create().put("term", "salty"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldOmitPrefixLengthWhenNoFuzziness() {
        SearchParams params = SearchParams.build().limit(10);
        TermQuery query = SearchQuery.term("salty")
            .boost(1.5)
            .field("field")
            .prefixLength(12);

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("term", "salty")
                .put("boost", 1.5)
                .put("field", "field")) //no prefix_length
            .put("size", 10);
        assertEquals(expected, query.export(params));
    }

    @Test
    public void shouldOmitPrefixLengthUnderOne() {
        SearchParams params = SearchParams.build().limit(10);
        TermQuery query = SearchQuery.term("salty")
            .boost(1.5)
            .field("field")
            .fuzziness(23)
            .prefixLength(-1);

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("term", "salty")
                .put("boost", 1.5)
                .put("field", "field")
                .put("fuzziness", 23))
            .put("size", 10);
        assertEquals(expected, query.export(params));
    }

    @Test
    public void shouldExportTermQueryWithAllOptions() {
        SearchParams params = SearchParams.build().limit(10);
        TermQuery query = SearchQuery.term("salty")
            .boost(1.5)
            .field("field")
            .fuzziness(23)
            .prefixLength(12);

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("term", "salty")
                .put("boost", 1.5)
                .put("field", "field")
                .put("fuzziness", 23)
                .put("prefix_length", 12))
            .put("size", 10);
        assertEquals(expected, query.export(params));
    }

}
