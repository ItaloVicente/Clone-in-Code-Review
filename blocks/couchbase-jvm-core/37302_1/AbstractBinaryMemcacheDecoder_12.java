package io.netty.handler.codec.memcache;

import io.netty.handler.codec.DecoderResult;

public interface MemcacheObject {

    DecoderResult getDecoderResult();

    void setDecoderResult(DecoderResult result);

}
