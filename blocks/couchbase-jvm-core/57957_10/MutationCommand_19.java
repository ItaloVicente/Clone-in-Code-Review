
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.kv.KeyValueHandler;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public enum Mutation {

    COUNTER(KeyValueHandler.OP_SUB_COUNTER),
    REPLACE(KeyValueHandler.OP_SUB_REPLACE),
    DICT_ADD(KeyValueHandler.OP_SUB_DICT_ADD),
    DICT_UPSERT(KeyValueHandler.OP_SUB_DICT_UPSERT),
    ARRAY_PUSH_FIRST(KeyValueHandler.OP_SUB_ARRAY_PUSH_FIRST),
    ARRAY_PUSH_LAST(KeyValueHandler.OP_SUB_ARRAY_PUSH_LAST),
    ARRAY_ADD_UNIQUE(KeyValueHandler.OP_SUB_ARRAY_ADD_UNIQUE),
    ARRAY_INSERT(KeyValueHandler.OP_SUB_ARRAY_INSERT);

    private final byte opCode;

    Mutation(byte opCode) {
        this.opCode = opCode;
    }

    public byte opCode() {
        return opCode;
    }
}
