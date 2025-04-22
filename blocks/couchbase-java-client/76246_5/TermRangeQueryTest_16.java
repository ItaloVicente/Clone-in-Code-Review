package com.couchbase.client.java.search.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.search.SearchQuery;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TermRangeQueryTest {

    @Test
    public void shouldFailIfNoBounds() {
        TermRangeQuery fts = SearchQuery.termRange();
        SearchQuery query = new SearchQuery("foo", fts);
        catchException(query).export();

        assertTrue(caughtException() instanceof NullPointerException);
        assertTrue(caughtException().getMessage().contains("min or max"));
    }

    @Test
    public void shouldAcceptMinOnly() {
        TermRangeQuery fts = SearchQuery.termRange().min("lower");
        SearchQuery query = new SearchQuery("foo", fts);
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("min", "lower"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldAcceptMaxOnly() {
        TermRangeQuery fts = SearchQuery.termRange().max("upper");
        SearchQuery query = new SearchQuery("foo", fts);
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("max", "upper"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldNotImplicitlySetDefaultsForInclusiveMinAndMax() {
        TermRangeQuery fts = SearchQuery.termRange()
            .boost(1.5)
            .field("field")
            .min("lower")
            .max("upper");
        SearchQuery query = new SearchQuery("foo", fts)
            .explain();

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("min", "lower")
                .put("max", "upper")
                .put("boost", 1.5)
                .put("field", "field"))
            .put("explain", true);
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportDateRangeQueryWithAllOptions() {
        TermRangeQuery fts = SearchQuery.termRange()
            .boost(1.5)
            .field("field")
            .min("lower", false)
            .max("upper", true);
        SearchQuery query = new SearchQuery("foo", fts)
            .explain();

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("min", "lower")
                .put("inclusive_min", false)
                .put("max", "upper")
                .put("inclusive_max", true)
                .put("boost", 1.5)
                .put("field", "field"))
            .put("explain", true);
        assertEquals(expected, query.export());
    }

}
