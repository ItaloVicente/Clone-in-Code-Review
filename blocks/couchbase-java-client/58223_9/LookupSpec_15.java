package com.couchbase.client.java.document.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class LookupResult {

    protected final String path;
    protected final Lookup operation;

    protected final ResponseStatus status;

    protected final Object value;

    protected LookupResult(String path, Lookup operation, ResponseStatus status, Object value) {
        this.path = path;
        this.operation = operation;
        this.status = status;
        this.value = value;
    }

    public static LookupResult createFatal(String path, Lookup operation, RuntimeException fatal) {
        return new LookupResult(path, operation, ResponseStatus.FAILURE, fatal);
    }

    public static LookupResult createGetResult(String path, ResponseStatus status, Object value) {
        return new LookupResult(path, Lookup.GET, status, value);
    }

    public static LookupResult createExistResult(String path, ResponseStatus status) {
        return new LookupResult(path, Lookup.EXIST, status, status.isSuccess());
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

    public boolean exists() {
        return status.isSuccess();
    }

    public Object value() {
        return value;
    }

    public boolean isFatal() {
        return status == ResponseStatus.FAILURE;
    }

    public Object valueOrThrow() {
        if (isFatal()) {
            throw (RuntimeException) value;
        }
        return value;
    }
}
