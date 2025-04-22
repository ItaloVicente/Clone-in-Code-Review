
package com.couchbase.client.core.endpoint.query.parser;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.query.GenericQueryResponse;
import io.netty.buffer.ByteBuf;

public interface QueryResponseParser {

    void initialize(CouchbaseRequest request, ByteBuf responseContent, ResponseStatus status);

    GenericQueryResponse parse(boolean lastChunk) throws Exception;

    void finishParsingAndReset();

    boolean isInitialized();
}
