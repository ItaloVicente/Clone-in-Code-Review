
package com.couchbase.client.core.utils.yasjl;

import static com.couchbase.client.core.utils.yasjl.JsonParserUtils.*;

import io.netty.buffer.ByteBufProcessor;

public class JsonArrayByteBufProcessor implements ByteBufProcessor {
    private boolean isString;
    private int count;
    private final JsonStringByteBufProcessor stProcessor;

    public JsonArrayByteBufProcessor(JsonStringByteBufProcessor stProcessor) {
        this.stProcessor = stProcessor;
        reset();
    }

    public void reset() {
        this.isString = false;
        this.count = 1;
        this.stProcessor.reset();
    }

    public boolean process(byte value) throws Exception {
        if (this.isString) {
            this.isString = this.stProcessor.process(value);
            return true;
        } else {
            switch (value) {
                case O_SQUARE:
                    this.count++;
                    return true;
                case C_SQUARE:
                    this.count--;
                    return count != 0;
                case JSON_ST:
                    this.isString = true;
                    return true;
                default:
                    return true;
            }
        }
    }
}
