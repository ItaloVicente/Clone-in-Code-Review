package com.couchbase.client.java.fts.queries;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class DateRangeQueryTest {

    @Test
    public void shouldFailIfNoBounds() {
        DateRangeQuery query = SearchQuery.dateRange();
        catchException(query).export();

        assertTrue(caughtException() instanceof NullPointerException);
        assertTrue(caughtException().getMessage().contains("start or end"));
    }

    @Test
    public void shouldAcceptEndOnly() {
        DateRangeQuery query = SearchQuery.dateRange().end("theEnd");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("end", "theEnd"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldAcceptStartOnly() {
        DateRangeQuery query = SearchQuery.dateRange().start("theStart");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("start", "theStart"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldNotImplicitlySetDefaultsForInclusiveStartAndEnd() {
        SearchParams params = SearchParams.build().explain();
        DateRangeQuery query = SearchQuery.dateRange()
            .boost(1.5)
            .field("field")
            .start("a")
            .end("b")
            .dateTimeParser("parser");

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("start", "a")
                .put("end", "b")
                .put("datetime_parser", "parser")
                .put("boost", 1.5)
                .put("field", "field"))
            .put("explain", true);
        assertEquals(expected, query.export(params));
    }

    @Test
    public void shouldIgnoreInclusiveStartWithNullStart() {
        DateRangeQuery query = SearchQuery.dateRange()
            .start(null, true)
            .end("b");

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("end", "b"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldIgnoreInclusiveEndWithNullEnd() {
        DateRangeQuery query = SearchQuery.dateRange()
            .start("a")
            .end(null, true);

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("start", "a"));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportDateRangeQueryWithAllOptions() {
        SearchParams params = SearchParams.build().explain();
        DateRangeQuery query = SearchQuery.dateRange()
            .boost(1.5)
            .field("field")
            .start("a", false)
            .end("b", true)
            .dateTimeParser("parser");

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("start", "a")
                .put("inclusive_start", false)
                .put("end", "b")
                .put("inclusive_end", true)
                .put("datetime_parser", "parser")
                .put("boost", 1.5)
                .put("field", "field"))
            .put("explain", true);
        assertEquals(expected, query.export(params));
    }

}
