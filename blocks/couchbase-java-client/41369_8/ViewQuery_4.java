package com.couchbase.client.java.error;

import com.couchbase.client.core.CouchbaseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewQueryException extends CouchbaseException implements Iterable<ViewQueryException.Error> {
    private List<Error> errors = new ArrayList<>();

    public ViewQueryException() {
        super();
    }

    public ViewQueryException(String message) {
        super(message);
    }

    public ViewQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewQueryException(Throwable cause) {
        super(cause);
    }

    public ViewQueryException(String error, String info, Throwable cause) {
        super(error + ": " + info, cause);
        errors.add(new Error(error, info));
    }

    public ViewQueryException(String error, String info) {
        super(error + ": " + info);
        addError(error, info);
    }

    public List<Error> errors() {
        return errors;
    }

    public void addError(final String error, final String info) {
        errors.add(new Error(error, info));
    }

    @Override
    public Iterator<Error> iterator() {
        return errors.iterator();
    }

    public class Error {
        private final String error;
        private final String info;

        public Error(String error, String info) {
            this.error = error;
            this.info = info;
        }

        public String error() {
            return error;
        }

        public String info() {
            return info;
        }
    }
}
