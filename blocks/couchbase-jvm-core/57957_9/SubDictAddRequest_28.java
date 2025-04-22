
package com.couchbase.client.core.message.kv.subdoc.simple;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.kv.KeyValueHandler;
import io.netty.buffer.Unpooled;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SubDeleteRequest extends AbstractSubdocMutationRequest {

    public SubDeleteRequest(String key, String path, String bucket, int expiration, long cas) {
        super(key, path, Unpooled.EMPTY_BUFFER, bucket, expiration, cas);
        if (path.isEmpty()) {
            cleanUpAndThrow(EXCEPTION_EMPTY_PATH);
        }
    }

    public SubDeleteRequest(String key, String path, String bucket) {
        this(key, path, bucket, 0, 0L);
    }

    @Override
    public byte opcode() {
        return KeyValueHandler.OP_SUB_DELETE;
    }
}
