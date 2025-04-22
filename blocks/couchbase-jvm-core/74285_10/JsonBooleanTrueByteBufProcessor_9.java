
package com.couchbase.client.core.utils.yasjl;

import io.netty.buffer.ByteBufProcessor;

public class JsonBooleanTrueByteBufProcessor implements ByteBufProcessor {
    private static final byte T1 = (byte)'t';
    private static final byte T2 = (byte)'r';
    private static final byte T3 = (byte)'u';
    private static final byte T4 = (byte)'e';

    private byte lastValue;

    public JsonBooleanTrueByteBufProcessor() {
        reset();
    }

    public void reset() {
        this.lastValue = T1;
    }

    public boolean process(byte value) throws Exception {
        switch (value) {
            case T2:
                if (this.lastValue == T1) {
                    this.lastValue = T2;
                    return true;
                }
                break;
            case T3:
                if (this.lastValue == T2) {
                    this.lastValue = T3;
                    return true;
                }
                break;
            case T4:
                if (this.lastValue == T3) {
                    reset();
                    return false;
                }
                break;
        }
        return false;
    }
}
