
package com.couchbase.client.core.message.kv.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.AbstractKeyValueResponse;
import com.couchbase.client.core.message.kv.MutationToken;
import io.netty.buffer.ByteBuf;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SimpleSubdocResponse extends AbstractKeyValueResponse {

    private final long cas;

    private final MutationToken mutationToken;

    public SimpleSubdocResponse(ResponseStatus status, short serverStatusCode, String bucket, ByteBuf content,
                                BinarySubdocRequest request, long cas, MutationToken mutationToken) {
        super(status, serverStatusCode, bucket, content, request);
        this.cas = cas;
        this.mutationToken = mutationToken;
    }

    @Override
    public BinarySubdocRequest request() {
        return (BinarySubdocRequest) super.request();
    }

    public long cas() {
        return this.cas;
    }

    public MutationToken mutationToken() {
        return mutationToken;
    }
}
