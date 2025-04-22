
package com.couchbase.client.core.message.kv.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.kv.KeyValueHandler;
import io.netty.buffer.ByteBuf;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SubDictAddRequest extends AbstractSubdocMutationRequest {

    public SubDictAddRequest(String key, String path, ByteBuf fragment, String bucket, int expiration, int flags) {
        this(key, path, bucket, expiration, fragment, flags);
    }

    public SubDictAddRequest(String key, String path, ByteBuf fragment, String bucket, int expiration) {
        this(key, path, bucket, expiration, fragment, 0);
    }

    public SubDictAddRequest(String key, String path, ByteBuf fragment, String bucket) {
        this(key, path, bucket, 0, fragment, 0);
    }

    public SubDictAddRequest(String key, String path, String bucket, int expiration, ByteBuf fragment, int flags) {
        super(key, path, bucket, expiration, fragment, flags, 0L);
    }

    @Override
    public byte opcode() {
        return KeyValueHandler.OP_SUB_DICT_ADD;
    }
}
