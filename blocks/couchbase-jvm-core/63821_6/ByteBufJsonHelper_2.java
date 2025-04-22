package com.couchbase.client.core.endpoint.util;

public abstract class AbstractStringAwareBufProcessor {

    private byte lastByte = 0;

    private byte beforeLastByte = 0;

    private boolean inString = false;

    protected boolean isEscaped(byte nextByte) {
        boolean result = false;
        if (inString) {
            if (nextByte == '\"') { //detected a potential closing quote
                boolean escaped = lastByte == '\\' && beforeLastByte != '\\';
                inString = escaped;
            }
            result = true;
        } else {
            if (nextByte == '\"') {
                inString = true;
                result = true;
            }
        }
        beforeLastByte = lastByte;
        lastByte = nextByte;
        return result;
    }
}
