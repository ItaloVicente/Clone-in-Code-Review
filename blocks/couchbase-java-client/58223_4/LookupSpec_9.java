package com.couchbase.client.java.document.subdoc;

import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;

public class LookupResult {

    private final String path;
    private final Lookup operation;

    private final ResponseStatus status;

    private final Object value;

    public LookupResult(String path, Lookup operation, ResponseStatus status, Object value) {
        this.path = path;
        this.operation = operation;
        this.status = status;
        this.value = value;
    }

    public String path() {
        return path;
    }

    public Lookup operation() {
        return operation;
    }

    public ResponseStatus status() {
        return status;
    }

    public Object value() {
        return value;
    }

    public boolean isSuccess() {
        return status.isSuccess() || (operation == Lookup.EXIST && value != null); //GET could have a null value and still be a success
    }
}
