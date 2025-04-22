
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.kv.KeyValueHandler;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public enum Lookup {

    GET(KeyValueHandler.OP_SUB_GET),
    EXIST(KeyValueHandler.OP_SUB_EXIST);

    private final byte opCode;

    Lookup(byte opCode) {
        this.opCode = opCode;
    }

    public byte opCode() {
        return opCode;
    }
}
