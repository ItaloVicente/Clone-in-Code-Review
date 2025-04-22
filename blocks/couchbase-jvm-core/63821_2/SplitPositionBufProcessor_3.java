package com.couchbase.client.core.endpoint.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufProcessor;

public class SplitPositionBufProcessor extends AbstractStringAwareBufProcessor implements ByteBufProcessor {

    private final char splitChar;

    private final boolean detectJsonString;

    public SplitPositionBufProcessor(char splitChar) {
        this(splitChar, false);
    }

    public SplitPositionBufProcessor(char splitChar, boolean detectJsonString) {
        this.splitChar = splitChar;
        this.detectJsonString = detectJsonString;
    }

    @Override
    public boolean process(final byte current) throws Exception {
        if (detectJsonString && isEscaped(current)) {
            return true;
        }

        if (current == splitChar) {
            return false;
        }
        return true;
    }
}
