package com.couchbase.client.core.endpoint.util;

import io.netty.buffer.ByteBufProcessor;

public class ClosingPositionBufProcessor implements ByteBufProcessor {

    private int openCount = 0;

    private final char openingChar;

    private final char closingChar;

    public ClosingPositionBufProcessor(char openingChar, char closingChar) {
        this.openingChar = openingChar;
        this.closingChar = closingChar;
    }

    @Override
    public boolean process(final byte current) throws Exception {
        if (current == openingChar) {
            openCount++;
        } else if (current == closingChar && openCount > 0) {
            openCount--;
            if (openCount == 0) {
                return false;
            }
        }
        return true;
    }
}
