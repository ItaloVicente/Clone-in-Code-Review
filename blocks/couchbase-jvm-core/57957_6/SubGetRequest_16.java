
package com.couchbase.client.core.message.kv.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.kv.KeyValueHandler;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SubExistRequest extends AbstractSubdocRequest {

    public SubExistRequest(String key, String path, String bucket) {
        super(key, path, bucket);
    }

    @Override
    public byte opcode() {
        return KeyValueHandler.OP_SUB_EXIST;
    }
}
