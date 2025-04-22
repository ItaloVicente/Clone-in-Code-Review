package com.couchbase.client.java.document.subdoc;

import java.util.Arrays;
import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.JsonDocument;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class MultiLookupResult {

    private final String documentId;

    private final List<LookupSpec> specs;
    private final List<LookupResult> results;
    private final boolean hasSuccess;
    private final boolean hasFailure;

    public MultiLookupResult(String documentId, List<LookupSpec> specs,
            List<LookupResult> results) {
        this.documentId = documentId;
        this.specs = specs;
        this.results = results;

        boolean hasSuccess = false;
        boolean hasFailure = false;
        for (LookupResult r : results) {
            if (r.status().isSuccess()) {
                hasSuccess = true;
            } else {
                hasFailure = true;
            }
        }
        this.hasFailure = hasFailure;
        this.hasSuccess = hasSuccess;
    }

    public MultiLookupResult(String documentId, LookupSpec[] specs, List<LookupResult> results) {
        this(documentId, Arrays.asList(specs), results);
    }

    public String documentId() {
        return documentId;
    }

    public List<LookupSpec> specs() {
        return specs;
    }

    public List<LookupResult> results() {
        return results;
    }

    public boolean hasSuccess() {
        return hasSuccess;
    }

    public boolean hasFailure() {
        return hasFailure;
    }

    public boolean isTotalSuccess() {
        return !hasFailure;
    }

    public boolean isTotalFailure() {
        return !hasSuccess;
    }
}
