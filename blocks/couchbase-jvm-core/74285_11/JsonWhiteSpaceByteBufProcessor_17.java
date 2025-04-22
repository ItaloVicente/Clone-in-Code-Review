
package com.couchbase.client.core.utils.yasjl;

import static com.couchbase.client.core.utils.yasjl.JsonParserUtils.*;

import io.netty.buffer.ByteBufProcessor;

public class JsonWhiteSpaceByteBufProcessor implements ByteBufProcessor {

    public boolean process(byte value) throws Exception {
        switch(value) {
            case WS_SPACE:
            case WS_TAB:
            case WS_LF:
            case WS_CR:
                return true;
            default:
                return false;
        }
    }
}
