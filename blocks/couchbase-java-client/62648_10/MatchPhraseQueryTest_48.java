package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatchPhraseQueryTest {

    @Test
    public void shouldExportMatchPhraseQuery() {
        MatchPhraseQuery query = SearchQuery.matchPhrase("salty beers");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create().put("match_phrase", "salty beers"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportMatchPhraseQueryWithAllOptions() {
        MatchPhraseQuery query = SearchQuery.matchPhrase("salty beers", SearchParams.build().limit(10))
            .boost(1.5)
            .field("field")
            .analyzer("analyzer");

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("match_phrase", "salty beers")
                .put("analyzer", "analyzer")
                .put("boost", 1.5)
                .put("field", "field"))
            .put("size", 10);
        assertEquals(expected, query.export());
    }

}
