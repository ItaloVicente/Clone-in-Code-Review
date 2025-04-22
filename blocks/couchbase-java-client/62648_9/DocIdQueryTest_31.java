package com.couchbase.client.java.fts.queries;

import static org.junit.Assert.*;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;
import org.junit.Test;

public class DocIdQueryTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailAtExecutionOnEmptyIds() throws Exception {
        SearchQuery.docId().export();
    }

    @Test
    public void shouldAddIdsToExistingList() throws Exception {
        DocIdQuery query = SearchQuery.docId("id1", "id2")
            .boost(1.5)
            .docIds("id3");

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("ids", JsonArray.from("id1", "id2", "id3"))
                .put("boost", 1.5));
        assertEquals(expected, query.export());
    }

    @Test
    public void shouldExportDocIdQueryWithAllOptions() {
        DocIdQuery query = SearchQuery.docId(SearchParams.build().limit(10), "id1", "id2")
            .boost(1.5)
            .docIds("id3");

        JsonObject expected = JsonObject.create()
            .put("query", JsonObject.create()
                .put("ids", JsonArray.from("id1", "id2", "id3"))
                .put("boost", 1.5))
            .put("size", 10);
        assertEquals(expected, query.export());
    }
}
