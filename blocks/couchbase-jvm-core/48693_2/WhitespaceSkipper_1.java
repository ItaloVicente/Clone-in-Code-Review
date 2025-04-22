package com.couchbase.client.core.endpoint.util;

import io.netty.buffer.ByteBufProcessor;

public class StringClosingPositionBufProcessor implements ByteBufProcessor {

    private boolean inString = false;
    private byte lastByte = 0;

    @Override
    public boolean process(byte value) throws Exception {
        boolean done;
        if (!inString && value == '"') {
            inString = true;
            done = false;
        } else if (inString && value == '"' && lastByte != '\\') {
            inString = false;
            done = true;
        } else {
            done = false;
        }

        lastByte = value;
        return !done;
    }
}
