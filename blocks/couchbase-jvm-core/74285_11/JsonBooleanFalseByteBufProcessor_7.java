
package com.couchbase.client.core.utils.yasjl;

import io.netty.buffer.ByteBufProcessor;

public class JsonBOMByteBufProcessor implements ByteBufProcessor {
    private static final byte BOM1 = (byte)0xEF;
    private static final byte BOM2 = (byte)0xBB;
    private static final byte BOM3 = (byte)0xBF;
    private byte lastValue;

    public JsonBOMByteBufProcessor() {
        reset();
    }

    private void reset() {
        this.lastValue = BOM1;
    }

    public boolean process(byte value) throws Exception {
        switch(value) {
            case BOM2:
                if (this.lastValue == BOM1) {
                    this.lastValue = BOM2;
                    return true;
                }
                break;
            case BOM3:
                if (this.lastValue == BOM2) {
                    return false;
                }
                break;
        }
        return false;
    }
}
