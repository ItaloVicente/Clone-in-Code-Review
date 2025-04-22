
package com.couchbase.client.core.utils.yasjl;

import static com.couchbase.client.core.utils.yasjl.JsonParserUtils.*;

import io.netty.buffer.ByteBufProcessor;

public class JsonStringByteBufProcessor implements ByteBufProcessor {
    private State currentState;

    private enum State {
        UNESCAPED, ESCAPED
    }

    public JsonStringByteBufProcessor() {
        this.currentState = State.UNESCAPED;
    }

    public void reset(){
        this.currentState = State.UNESCAPED;
    }

    public boolean process(byte value) throws Exception {
        switch(value) {
            case JSON_ES:
                if (this.currentState == State.UNESCAPED) {
                    this.currentState = State.ESCAPED;
                } else {
                    this.currentState = State.UNESCAPED;
                }
                return true;
            case JSON_ST:
                if (this.currentState == State.ESCAPED) {
                    this.currentState = State.UNESCAPED;
                    return true;
                } else {
                    reset();
                    return false;
                }
            default:
                if (this.currentState == State.ESCAPED) {
                    this.currentState = State.UNESCAPED;
                }
                return true;
        }
    }
}
