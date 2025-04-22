
package com.couchbase.client.java.bucket.api;

import com.couchbase.client.core.message.kv.GetRequest;
import com.couchbase.client.java.document.json.JsonObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UtilsTest {

    @Test
    public void shouldFormatTimeout() {
        GetRequest request = new GetRequest("key", "bucket");

        String result = Utils.formatTimeout(request, 1000);

        JsonObject converted = JsonObject.fromJson(result);
        assertEquals("bucket", converted.getString("b"));
        assertEquals("kv", converted.getString("s"));
        assertEquals(1000, (int) converted.getInt("t"));
        assertEquals("0x0", converted.getString("i"));
        assertFalse(converted.containsKey("c"));
        assertFalse(converted.containsKey("l"));
        assertFalse(converted.containsKey("r"));

        request.lastLocalId("0E637F38FA73001A/FFFFFFFFFA809609");
        request.lastLocalSocket("127.0.0.1:60119");
        request.lastRemoteSocket("127.0.0.1:11210");

        result = Utils.formatTimeout(request, 1000);
        converted = JsonObject.fromJson(result);

        assertEquals("0E637F38FA73001A/FFFFFFFFFA809609", converted.getString("c"));
        assertEquals("127.0.0.1:11210", converted.getString("r"));
        assertEquals("127.0.0.1:60119", converted.getString("l"));
    }

}
