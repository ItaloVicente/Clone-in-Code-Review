
package com.couchbase.client.core.endpoint.kv;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class KeyValueFeatureHandlerTest {

    private static final ObjectMapper JACKSON = new ObjectMapper();

    @Test
    public void shouldConvertUserAgent() throws Exception {
        byte[] result = KeyValueFeatureHandler.generateAgentJson(
            "my-agent",
            1234,
            5678
        );

        HashMap decoded = JACKSON.readValue(result, HashMap.class);
        assertEquals("my-agent", decoded.get("a"));
        assertEquals("00000000000004D2/000000000000162E", decoded.get("i"));
    }

    @Test
    public void shouldCutTooLongAgent() throws Exception {
        String tooLongAgent = "foobar";
        while (tooLongAgent.length() < 200) {
            tooLongAgent += tooLongAgent;
        }
        byte[] result = KeyValueFeatureHandler.generateAgentJson(
            tooLongAgent,
            1234,
            5678
        );

        HashMap decoded = JACKSON.readValue(result, HashMap.class);
        assertEquals(200, ((String) decoded.get("a")).length());
    }

}
