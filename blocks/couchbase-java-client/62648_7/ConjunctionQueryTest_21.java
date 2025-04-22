package com.couchbase.client.java.fts.queries;

import static org.junit.Assert.assertEquals;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class ConjunctionQueryTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailExportWhenNoChild() {
        SearchQuery.disjuncts().export();
    }

    @Test
    public void shouldExportConjunctionQueryWithInnerBoost() {
        PrefixQuery inner = SearchQuery.prefix("someterm").boost(2);
        ConjunctionQuery query = SearchQuery.conjuncts(inner);

        JsonObject expectedInner = JsonObject.create().put("prefix", "someterm").put("boost", 2.0);
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("conjuncts", JsonArray.from(expectedInner)));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldRemoveSearchParamsFromInner() {
        PrefixQuery inner = SearchQuery.prefix("someterm", SearchParams.build().explain());
        ConjunctionQuery query = SearchQuery.conjuncts(inner);

        JsonObject expectedInner = JsonObject.create().put("prefix", "someterm"); //no explain
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("conjuncts", JsonArray.from(expectedInner)));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportConjunctionQueryWithDefaults() {
        PrefixQuery inner = SearchQuery.prefix("someterm");
        ConjunctionQuery query = SearchQuery.conjuncts(inner);

        JsonObject expectedInner = JsonObject.create().put("prefix", "someterm");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("conjuncts", JsonArray.from(expectedInner)));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldAddChildQueriesToExistingQueries() {
        PrefixQuery innerA = SearchQuery.prefix("someterm").boost(2.0);
        PrefixQuery innerB = SearchQuery.prefix("termB");
        PrefixQuery innerC = SearchQuery.prefix("termC");

        ConjunctionQuery query = SearchQuery.conjuncts(innerA, innerB)
                .and(innerC);

        JsonObject expectedInnerA = JsonObject.create().put("prefix", "someterm").put("boost", 2d);
        JsonObject expectedInnerB = JsonObject.create().put("prefix", "termB");
        JsonObject expectedInnerC = JsonObject.create().put("prefix", "termC");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                    .put("conjuncts", JsonArray.from(expectedInnerA, expectedInnerB, expectedInnerC)));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportDisjunctionQueryWithAllOptions() {
        PrefixQuery innerA = SearchQuery.prefix("someterm").boost(2.0);
        PrefixQuery innerB = SearchQuery.prefix("termB");
        PrefixQuery innerC = SearchQuery.prefix("termC");

        ConjunctionQuery query = SearchQuery.conjuncts(SearchParams.build().explain())
            .boost(1.5)
            .and(innerA, innerB, innerC);

        JsonObject expectedInnerA = JsonObject.create().put("prefix", "someterm").put("boost", 2d);
        JsonObject expectedInnerB = JsonObject.create().put("prefix", "termB");
        JsonObject expectedInnerC = JsonObject.create().put("prefix", "termC");
        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("boost", 1.5)
                .put("conjuncts", JsonArray.from(expectedInnerA, expectedInnerB, expectedInnerC)))
            .put("explain", true);
        assertEquals(expected, query.export());
    }

}
