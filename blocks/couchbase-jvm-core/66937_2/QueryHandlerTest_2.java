
package com.couchbase.client.core.message.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.AbstractCouchbaseResponse;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;
import io.netty.buffer.ByteBuf;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class RawQueryResponse extends AbstractCouchbaseResponse {

    private final ByteBuf jsonResponse;
    private final int httpStatusCode;
    private final String httpStatusMsg;

    public RawQueryResponse(ResponseStatus status, CouchbaseRequest request, ByteBuf jsonResponse,
                            int httpStatusCode, String httpStatusMsg) {
        super(status, request);
        this.jsonResponse = jsonResponse;
        this.httpStatusCode = httpStatusCode;
        this.httpStatusMsg = httpStatusMsg;
    }

    public ByteBuf jsonResponse() {
        return this.jsonResponse;
    }

    public int httpStatusCode() {
        return this.httpStatusCode;
    }

    public String httpStatusMsg() {
        return this.httpStatusMsg;
    }
}
