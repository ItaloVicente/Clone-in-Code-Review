
package com.couchbase.client.core.utils.yasjl;

import io.netty.buffer.ByteBufProcessor;

public class JsonBooleanFalseByteBufProcessor implements ByteBufProcessor {
    private static final byte F1 = (byte)'f';
    private static final byte F2 = (byte)'a';
    private static final byte F3 = (byte)'l';
    private static final byte F4 = (byte)'s';
    private static final byte F5 = (byte)'e';

    private byte lastValue;

    public JsonBooleanFalseByteBufProcessor() {
        this.lastValue = F1;
    }

    public void reset() {
        this.lastValue = F1;
    }

    public boolean process(byte value) throws Exception {
        switch (value) {
            case F2:
                if (this.lastValue == F1) {
                    this.lastValue = F2;
                    return true;
                }
                break;
            case F3:
                if (this.lastValue == F2) {
                    this.lastValue = F3;
                    return true;
                }
                break;
            case F4:
                if (this.lastValue == F3) {
                    this.lastValue = F4;
                    return true;
                }
                break;
            case F5:
                if (this.lastValue == F4) {
                    reset();
                    return false;
                }
                break;
        }
        return false;
    }
}
