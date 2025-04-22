package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.ResponseStatus;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

public class StatResponse extends AbstractKeyValueResponse {

    private final String key;
    private final String value;


    public StatResponse(ResponseStatus status, short serverStatusCode, String key, String value,
                        String bucket, CouchbaseRequest request) {
        super(status, serverStatusCode, bucket, null, request);
        this.key = key;
        this.value = value;
    }

    public String key() {
        return key;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("StatResponse{")
                .append("bucket='").append(bucket()).append('\'')
                .append(", status=").append(status()).append(" (").append(serverStatusCode()).append(')')
                .append(", key=").append(key)
                .append(", value=").append(value)
                .append(", request=").append(request())
                .append('}').toString();
    }

}
