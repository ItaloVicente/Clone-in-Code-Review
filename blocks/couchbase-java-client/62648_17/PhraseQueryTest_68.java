package com.couchbase.client.java.fts.queries;

import static com.googlecode.catchexception.CatchException.verifyException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class PhraseQueryTest {

    @Test
    public void shouldExportPhraseQuery() {
        PhraseQuery query = SearchQuery.phrase("salty", "beers");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create().put("terms", JsonArray.from("salty", "beers")));
        assertEquals(expected, query.export());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNullPointerOnNullTermsDuringConstruction() {
        SearchQuery.phrase(null);
    }

    @Test
    public void shouldIllegalArgumentOnEmptyTermsDuringExport() {
        PhraseQuery query = SearchQuery.phrase();
        assertNotNull(query);
        verifyException(query, IllegalArgumentException.class).export();
    }

    @Test
    public void shouldExportPhraseQueryWithAllOptions() {
        SearchParams params = SearchParams.build().limit(10);
        PhraseQuery query = SearchQuery.phrase("salty", "beers")
            .boost(1.5)
            .field("field");

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("terms", JsonArray.from("salty", "beers"))
                .put("boost", 1.5)
                .put("field", "field"))
            .put("size", 10);
        assertEquals(expected, query.export(params));
    }

}
