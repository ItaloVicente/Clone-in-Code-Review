
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.ResponseStatusConverter;
import com.couchbase.client.core.endpoint.kv.KeyValueStatus;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.AbstractKeyValueResponse;
import com.couchbase.client.core.message.kv.MutationToken;
import com.couchbase.client.core.message.kv.subdoc.BinarySubdocMultiMutationRequest;
import com.couchbase.client.core.message.kv.subdoc.BinarySubdocRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class MultiMutationResponse extends AbstractKeyValueResponse {

    private final long cas;

    private final MutationToken mutationToken;
    private final int firstErrorIndex;
    private final ResponseStatus firstErrorStatus;

    public MultiMutationResponse(ResponseStatus status, short serverStatusCode, String bucket, int firstErrorIndex, short firstErrorStatusCode,
                                BinarySubdocMultiMutationRequest request, long cas, MutationToken mutationToken) { //do cas and muto make sense here?
        super(status, serverStatusCode, bucket, Unpooled.EMPTY_BUFFER, request);
        this.cas = cas;
        this.mutationToken = mutationToken;
        this.firstErrorIndex = firstErrorIndex;
        if (firstErrorIndex == -1) {
            this.firstErrorStatus = ResponseStatus.FAILURE;
        } else {
            this.firstErrorStatus = ResponseStatusConverter.fromBinary(firstErrorStatusCode);
        }
    }

    public MultiMutationResponse(ResponseStatus status, short serverStatusCode, String bucket,
                                 BinarySubdocMultiMutationRequest request, long cas, MutationToken mutationToken) { //do cas and muto make sense here?
        super(status, serverStatusCode, bucket, Unpooled.EMPTY_BUFFER, request);
        this.cas = cas;
        this.mutationToken = mutationToken;
        this.firstErrorIndex = -1;
        this.firstErrorStatus = ResponseStatus.FAILURE;
    }

    public MultiMutationResponse(String bucket, BinarySubdocMultiMutationRequest request, long cas, MutationToken token) {
        super(ResponseStatus.SUCCESS, KeyValueStatus.SUCCESS.code(), bucket, Unpooled.EMPTY_BUFFER, request);
        this.cas = cas;
        this.mutationToken = token;
        this.firstErrorIndex = -1;
        this.firstErrorStatus = ResponseStatus.SUCCESS;
    }

    @Override
    public BinarySubdocMultiMutationRequest request() {
        return (BinarySubdocMultiMutationRequest) super.request();
    }

    public long cas() {
        return this.cas;
    }

    public MutationToken mutationToken() {
        return mutationToken;
    }

    public int firstErrorIndex() {
        return firstErrorIndex;
    }

    public ResponseStatus firstErrorStatus() {
        return firstErrorStatus;
    }
}
