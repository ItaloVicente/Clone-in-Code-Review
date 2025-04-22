
package com.couchbase.client.core.utils.yasjl;

import static com.couchbase.client.core.utils.yasjl.JsonParserUtils.*;

import io.netty.buffer.ByteBufProcessor;

public class JsonNumberByteBufProcessor implements ByteBufProcessor {

    public JsonNumberByteBufProcessor() {
    }

    public boolean process(byte value) throws Exception {
        if (value == (byte) 'e' || value == (byte) 'E') {
            return true;
        }
        if (value >= (byte) '0' && value <= (byte) '9') {
            return true;
        }
        return value == JSON_MINUS || value == JSON_PLUS || value == (byte) '.';
    }
}
