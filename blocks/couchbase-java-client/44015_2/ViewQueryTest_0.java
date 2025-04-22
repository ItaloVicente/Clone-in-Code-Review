package com.couchbase.client.java;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.util.ClusterDependentTest;
import com.couchbase.client.java.view.*;
import org.junit.BeforeClass;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SpatialViewQueryTest extends ClusterDependentTest {

    private static final JsonObject[] FIXTURES = new JsonObject[] {
        JsonObject.create().put("name", "Vienna").put("lon", 16.36962890625).put("lat", 48.21094727794909),
        JsonObject.create().put("name", "Berlin").put("lon", 13.3978271484375).put("lat", 52.51622086393074),
        JsonObject.create().put("name", "Paris").put("lon", 2.373046875).put("lat", 48.864714761802794),
        JsonObject.create().put("name", "New York").put("lon", -73.970947265625).put("lat", 40.75557964275591),
        JsonObject.create().put("name", "San Francisco").put("lon", -122.47009277343749).put("lat", 37.76202988573211)
    };

    @BeforeClass
    public static void setupSpatialViews() {
        Observable
            .from(FIXTURES)
            .flatMap(new Func1<JsonObject, Observable<JsonDocument>>() {
                @Override
                public Observable<JsonDocument> call(JsonObject content) {
                    String id = "city::" + content.getString("name");
                    content.put("type", "city");
                    return bucket().async().upsert(JsonDocument.create(id, content));
                }
            })
            .last()
            .toBlocking()
            .single();

        DesignDocument designDoc = DesignDocument.create("cities", Arrays.asList(
            SpatialView.create("by_location", "function (doc) { if (doc.type == \"city\") { emit([doc.lon, doc.lat], "
                + "null); } }")
        ));

        DesignDocument stored = bucketManager().getDesignDocument("cities");
        if (stored == null || !stored.equals(designDoc)) {
            bucketManager().upsertDesignDocument(designDoc);
        }
    }

    @Test
    public void shouldQuerySpatial() {
        SpatialViewResult result = bucket().query(SpatialViewQuery.from("cities", "by_location").stale(Stale.FALSE));
        List<SpatialViewRow> allRows = result.allRows();
        assertEquals(FIXTURES.length, allRows.size());

        for (SpatialViewRow row : allRows) {
            assertNotNull(row.id());
            assertEquals(2, row.key().size());
            assertNull(row.geometry());
            assertNull(row.value());
        }
    }

}
