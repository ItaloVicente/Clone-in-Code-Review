package com.couchbase.client.java.error;

public class FtsServerOverloadException extends TemporaryFailureException {

    public FtsServerOverloadException(String payload) {
        super("Search server is overloaded. Details: " + payload);
    }
}
