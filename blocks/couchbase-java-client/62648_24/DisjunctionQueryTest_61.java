package com.couchbase.client.java.fts.queries;

import static org.junit.Assert.assertEquals;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class DisjunctionQueryTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailExportWhenNoChild() {
        SearchQuery.disjuncts().export();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailExportWhenFewerChildThanMinimum() {
        DisjunctionQuery query = SearchQuery.disjuncts(SearchQuery.prefix("someterm")).min(2);
        query.export();
    }

    @Test
    public void shouldExportDisjunctionQueryWithInnerBoost() {
        PrefixQuery inner = SearchQuery.prefix("someterm").boost(2);
        DisjunctionQuery query = SearchQuery.disjuncts(inner);

        JsonObject expectedInner = JsonObject.create().put("prefix", "someterm").put("boost", 2.0);
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("disjuncts", JsonArray.from(expectedInner)));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportDisjunctionQueryWithDefaults() {
        PrefixQuery inner = SearchQuery.prefix("someterm");
        DisjunctionQuery query = SearchQuery.disjuncts(inner);

        JsonObject expectedInner = JsonObject.create().put("prefix", "someterm");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("disjuncts", JsonArray.from(expectedInner)));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldAddChildQueriesToExistingQueries() {
        PrefixQuery innerA = SearchQuery.prefix("someterm").boost(2.0);
        PrefixQuery innerB = SearchQuery.prefix("termB");
        PrefixQuery innerC = SearchQuery.prefix("termC");

        DisjunctionQuery query = SearchQuery.disjuncts(innerA, innerB)
                .or(innerC);

        JsonObject expectedInnerA = JsonObject.create().put("prefix", "someterm").put("boost", 2d);
        JsonObject expectedInnerB = JsonObject.create().put("prefix", "termB");
        JsonObject expectedInnerC = JsonObject.create().put("prefix", "termC");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                    .put("disjuncts", JsonArray.from(expectedInnerA, expectedInnerB, expectedInnerC)));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportDisjunctionQueryWithAllOptions() {
        SearchParams params = SearchParams.build().explain();
        PrefixQuery innerA = SearchQuery.prefix("someterm").boost(2.0);
        PrefixQuery innerB = SearchQuery.prefix("termB");
        PrefixQuery innerC = SearchQuery.prefix("termC");

        DisjunctionQuery query = SearchQuery.disjuncts()
            .boost(1.5)
            .min(2)
            .or(innerA, innerB, innerC);

        JsonObject expectedInnerA = JsonObject.create().put("prefix", "someterm").put("boost", 2d);
        JsonObject expectedInnerB = JsonObject.create().put("prefix", "termB");
        JsonObject expectedInnerC = JsonObject.create().put("prefix", "termC");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("boost", 1.5)
                .put("min", 2)
                .put("disjuncts", JsonArray.from(expectedInnerA, expectedInnerB, expectedInnerC)))
            .put("explain", true);
        assertEquals(expected, query.export(params));
    }

}
