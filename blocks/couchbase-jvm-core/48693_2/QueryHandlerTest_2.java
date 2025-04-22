package com.couchbase.client.core.endpoint.util;

import io.netty.buffer.ByteBufProcessor;

public class WhitespaceSkipper implements ByteBufProcessor {
    @Override
    public boolean process(byte current) throws Exception {
        return current == ' ' || current == '\t'
            || current == '\n' || current == '\r';
    }
}
