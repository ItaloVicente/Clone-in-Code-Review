
package com.couchbase.client.core.message.kv.subdoc.simple;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.kv.KeyValueHandler;
import io.netty.buffer.ByteBuf;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SubDictUpsertRequest extends AbstractSubdocMutationRequest {

    public SubDictUpsertRequest(String key, String path, ByteBuf fragment, String bucket) {
        this(key, path, fragment, bucket, 0, 0L);
    }

    public SubDictUpsertRequest(String key, String path, ByteBuf fragment, String bucket, int expiration, long cas) {
        super(key, path, fragment, bucket, expiration, cas);
    }

    @Override
    public byte opcode() {
        return KeyValueHandler.OP_SUB_DICT_UPSERT;
    }
}
