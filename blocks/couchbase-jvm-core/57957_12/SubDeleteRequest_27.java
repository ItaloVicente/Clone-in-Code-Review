
package com.couchbase.client.core.message.kv.subdoc.simple;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.kv.KeyValueHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SubCounterRequest extends AbstractSubdocMutationRequest {
    public SubCounterRequest(String key, String path, long delta, String bucket, int expiration, long cas) {
        super(key, path, deltaToFragment(delta), bucket, expiration, cas);
        if (path.isEmpty()) {
            cleanUpAndThrow(EXCEPTION_EMPTY_PATH);
        }
    }

    public SubCounterRequest(String key, String path, long delta, String bucket) {
        this(key, path, delta, bucket, 0, 0L);
    }

    private static ByteBuf deltaToFragment(long delta) {
        String sDelta = "" + delta;
        return Unpooled.copiedBuffer(sDelta, CharsetUtil.UTF_8);
    }

    @Override
    public byte opcode() {
        return KeyValueHandler.OP_SUB_COUNTER;
    }
}
