package com.couchbase.client.java;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.util.ClusterDependentTest;
import org.junit.Test;

public class JsonDocumentTest extends ClusterDependentTest {

    @Test
    public void testNumberConversion() {
        JsonObject jsonObject = JsonObject.empty().put("value", 0L);
        bucket().insert(JsonDocument.create("idTest", jsonObject));
        JsonDocument convertedDoc = bucket().get("idTest");
        JsonObject value = convertedDoc.content();

        assertThat(value.getLong("value"), notNullValue());
        assertThat(value.getDouble("value"), notNullValue());
        assertThat(value.getInt("value"), notNullValue());
    }
}
