package com.couchbase.client.java.util;

import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import rx.functions.Action;
import rx.functions.Action1;

public class Buffers {

    public static final Action1<ByteBuf> ACTION_RELEASE = new Action1<ByteBuf>() {
        @Override
        public void call(ByteBuf byteBuf) {
            if (byteBuf != null && byteBuf.refCnt() > 0) {
                byteBuf.release();
            }
        }
    };
}
