package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;

public interface LastMemcacheContent extends MemcacheContent {

    LastMemcacheContent EMPTY_LAST_CONTENT = new LastMemcacheContent() {

        @Override
        public LastMemcacheContent copy() {
            return EMPTY_LAST_CONTENT;
        }

        @Override
        public LastMemcacheContent retain(int increment) {
            return this;
        }

        @Override
        public LastMemcacheContent retain() {
            return this;
        }

        @Override
        public LastMemcacheContent duplicate() {
            return this;
        }

        @Override
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        @Override
        public DecoderResult getDecoderResult() {
            return DecoderResult.SUCCESS;
        }

        @Override
        public void setDecoderResult(DecoderResult result) {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public int refCnt() {
            return 1;
        }

        @Override
        public boolean release() {
            return false;
        }

        @Override
        public boolean release(int decrement) {
            return false;
        }
    };

    @Override
    LastMemcacheContent copy();

    @Override
    LastMemcacheContent retain(int increment);

    @Override
    LastMemcacheContent retain();

    @Override
    LastMemcacheContent duplicate();
}
