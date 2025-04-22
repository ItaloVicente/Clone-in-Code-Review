
package com.couchbase.client.core.message.kv.subdoc.simple;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.kv.KeyValueHandler;
import io.netty.buffer.ByteBuf;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SubArrayRequest extends AbstractSubdocMutationRequest {

    private final ArrayOperation arrayOp;

    public SubArrayRequest(String key, String path, ArrayOperation arrayOp, ByteBuf fragment, String bucket, int expiration, long cas) {
        super(key, path, fragment, bucket, expiration, cas);
        this.arrayOp = arrayOp;
    }

    public SubArrayRequest(String key, String path, ArrayOperation arrayOp, ByteBuf fragment, String bucket) {
        this(key, path, arrayOp, fragment, bucket, 0, 0L);
    }

    @Override
    public byte opcode() {
        return arrayOp.opCode();
    }

    public ArrayOperation arrayOperation() {
        return arrayOp;
    }

    public enum ArrayOperation {
        PUSH_FIRST(KeyValueHandler.OP_SUB_ARRAY_PUSH_FIRST),
        PUSH_LAST(KeyValueHandler.OP_SUB_ARRAY_PUSH_LAST),
        INSERT(KeyValueHandler.OP_SUB_ARRAY_INSERT),
        ADD_UNIQUE(KeyValueHandler.OP_SUB_ARRAY_ADD_UNIQUE);

        private byte opCode;

        ArrayOperation(byte opCode) {
            this.opCode = opCode;
        }

        public byte opCode() {
            return this.opCode;
        }
    }
}
