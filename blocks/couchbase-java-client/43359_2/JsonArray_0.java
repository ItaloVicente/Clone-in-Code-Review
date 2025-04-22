package com.couchbase.client.java;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import com.couchbase.client.java.document.JsonArrayDocument;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.util.ClusterDependentTest;
import org.junit.Test;

public class JsonDocumentTest extends ClusterDependentTest {

    @Test
    public void testNumberConversionInJsonObject() {
        JsonObject jsonObject = JsonObject.empty().put("value", 0L);
        bucket().insert(JsonDocument.create("jsonObjectTestDocument", jsonObject));
        JsonDocument convertedDoc = bucket().get("jsonObjectTestDocument");
        JsonObject value = convertedDoc.content();

        assertThat(value.getLong("value"), notNullValue());
        assertThat(value.getDouble("value"), notNullValue());
        assertThat(value.getInt("value"), notNullValue());
    }

    @Test
    public void testNumberConversionInJsonArray() {
        JsonArray jsonArray = JsonArray.empty().add(0L);
        bucket().insert(JsonArrayDocument.create("jsonArrayTestDocument", jsonArray));
        JsonArrayDocument convertedDoc = bucket().get("jsonArrayTestDocument", JsonArrayDocument.class);
        JsonArray value = convertedDoc.content();

        assertThat(value.getLong(0), notNullValue());
        assertThat(value.getDouble(0), notNullValue());
        assertThat(value.getInt(0), notNullValue());
    }
}
