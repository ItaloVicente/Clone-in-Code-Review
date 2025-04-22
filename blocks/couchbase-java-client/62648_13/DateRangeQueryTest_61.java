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
                .put("end", "theEnd")
                .put("inclusive_end", false));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldAcceptStartOnly() {
        DateRangeQuery query = SearchQuery.dateRange().start("theStart");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("start", "theStart")
                .put("inclusive_start", true));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExplicitlySetDefaultsForInclusiveStartAndEnd() {
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
                .put("inclusive_start", true)
                .put("end", "b")
                .put("inclusive_end", false)
                .put("datetime_parser", "parser")
                .put("boost", 1.5)
                .put("field", "field"))
            .put("explain", true);
        assertEquals(expected, query.export(params));
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
