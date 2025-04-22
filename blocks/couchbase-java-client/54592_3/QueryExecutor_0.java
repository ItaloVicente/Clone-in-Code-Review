package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.java.document.json.JsonObject;

public class QueryExecutionException extends CouchbaseException {

    private final JsonObject n1qlError;

    public QueryExecutionException(String message, JsonObject n1qlError) {
        super(message);
        this.n1qlError = n1qlError == null ? JsonObject.empty() : n1qlError;
    }

    public QueryExecutionException(String message, JsonObject n1qlError, Throwable cause) {
        super(message, cause);
        this.n1qlError = n1qlError == null ? JsonObject.empty() : n1qlError;
    }

    public JsonObject getN1qlError() {
        return this.n1qlError;
    }
}
