package com.couchbase.client.java.document.json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class JsonArrayTest {

    @Test
    public void shouldEqualBasedOnItsProperties() {
        JsonArray arr1 = JsonArray.create().add("foo").add("bar");
        JsonArray arr2 = JsonArray.create().add("foo").add("bar");
        assertEquals(arr1, arr2);

        arr1 = JsonArray.create().add("foo").add("baz");
        arr2 = JsonArray.create().add("foo").add("bar");
        assertNotEquals(arr1, arr2);

        arr1 = JsonArray.create().add("foo").add("bar").add("baz");
        arr2 = JsonArray.create().add("foo").add("bar");
        assertNotEquals(arr1, arr2);
    }

}
