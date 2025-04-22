package com.couchbase.client.core.message.query;

import com.couchbase.client.core.message.AbstractCouchbaseRequest;
import com.couchbase.client.core.message.CouchbaseResponse;
import rx.subjects.ReplaySubject;

public class GenericQueryRequest extends AbstractCouchbaseRequest implements QueryRequest {

    private final String query;

    public GenericQueryRequest(String query, String bucket, String password) {
        super(bucket, password, ReplaySubject.<CouchbaseResponse>create());
        this.query = query;
    }

    public String query() {
        return query;
    }
}
