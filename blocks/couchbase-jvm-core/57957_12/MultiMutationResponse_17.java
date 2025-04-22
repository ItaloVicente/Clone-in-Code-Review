
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.AbstractKeyValueResponse;
import com.couchbase.client.core.message.kv.subdoc.BinarySubdocMultiLookupRequest;
import io.netty.buffer.Unpooled;

import java.util.List;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class MultiLookupResponse extends AbstractKeyValueResponse {

    private final List<LookupResult> responses;

    public MultiLookupResponse(ResponseStatus status, short serverStatusCode, String bucket, List<LookupResult> responses,
                               BinarySubdocMultiLookupRequest request) {
        super(status, serverStatusCode, bucket, Unpooled.EMPTY_BUFFER, request);
        this.responses = responses;
    }

    @Override
    public BinarySubdocMultiLookupRequest request() {
        return (BinarySubdocMultiLookupRequest) super.request();
    }

    public List<LookupResult> responses() {
        return responses;
    }
}
