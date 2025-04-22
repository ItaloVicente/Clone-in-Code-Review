package com.couchbase.client.java.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.json.JsonValue;
import com.couchbase.client.java.query.consistency.ScanConsistency;

public class QueryParams {

    private String serverSideTimeout;
    private ScanConsistency consistency;
    private JsonValue scanVector;
    private String scanWait;
    private JsonArray creds;
    private String clientContextId;

    private QueryParams() { }

    public void injectParams(JsonObject queryJson) {
        if (this.serverSideTimeout != null) {
            queryJson.put("timeout", this.serverSideTimeout);
        }
        if (this.consistency == ScanConsistency.AT_PLUS && this.scanVector != null) {
                queryJson.put("scan_consistency", this.consistency.n1ql());
                queryJson.put("scan_vector", this.scanVector);
        } else if (this.consistency != null) {
            queryJson.put("scan_consistency", this.consistency.n1ql());
        }
        if (this.scanWait != null
                && (ScanConsistency.AT_PLUS == this.consistency
                || ScanConsistency.REQUEST_PLUS == this.consistency
                || ScanConsistency.STATEMENT_PLUS == this.consistency)) {
            queryJson.put("scan_wait", this.scanWait);
        }
        if (this.creds != null && !this.creds.isEmpty()) {
            queryJson.put("creds", this.creds);
        }
        if (this.clientContextId != null) {
            queryJson.put("client_context_id", this.clientContextId);
        }
    }
    
    private static String durationToN1qlFormat(long duration, TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS:
                return duration + "ns";
            case MICROSECONDS:
                return duration + "us";
            case MILLISECONDS:
                return duration + "ms";
            case SECONDS:
                return duration + "s";
            case MINUTES:
                return duration + "m";
            case HOURS:
                return duration + "h";
            case DAYS:
            default:
                return unit.toHours(duration) + "h";
        }
    }

    public static QueryParams build() {
        return new QueryParams();
    }

    public QueryParams serverSideTimeout(long timeout, TimeUnit unit) {
        this.serverSideTimeout = durationToN1qlFormat(timeout, unit);
        return this;
    }

    public QueryParams addCredential(String bucket, String password) {
        if (this.creds == null) {
            this.creds = JsonArray.empty();
        }
        this.creds.add(JsonObject.create()
            .put("user", "local:" + bucket)
            .put("pass", password));
        return this;
    }

    public QueryParams addAdminCredential(String adminName, String password) {
        if (this.creds == null) {
            this.creds = JsonArray.empty();
        }
        this.creds.add(JsonObject.create()
                                 .put("user", "admin:" + adminName)
                                 .put("pass", password));
        return this;
    }

    public QueryParams withContextId(String clientContextId) {
        this.clientContextId = clientContextId;
        return this;
    }

    public QueryParams consistencyAtPlus(int[] scanVector) {
        if (scanVector.length != 1024) {
            throw new IllegalArgumentException("Full Scan Vector must contain seqno for all 1024 vbuckets");
        }
        this.consistency = ScanConsistency.AT_PLUS;
        List<Integer> fullVector = new ArrayList<Integer>(scanVector.length);
        for (int i : scanVector) {
            fullVector.add(i);
        }
        this.scanVector = JsonArray.from(fullVector);
        return this;
    }

    public QueryParams consistencyAtPlus(Map<String, Integer> sparseScanVector) {
        this.consistency = ScanConsistency.AT_PLUS;
        this.scanVector = JsonObject.from(sparseScanVector);
        return this;
    }

    public QueryParams consistencyNotBounded() {
        this.consistency = ScanConsistency.NOT_BOUNDED;
        this.scanVector = null;
        this.scanWait = null;
        return this;
    }

    public QueryParams consistencyRequestPlus() {
        this.consistency = ScanConsistency.REQUEST_PLUS;
        this.scanVector = null;
        return this;
    }

    public QueryParams consistencyStatementPlus() {
        this.consistency = ScanConsistency.STATEMENT_PLUS;
        this.scanVector = null;
        return this;
    }

    public QueryParams scanWait(long wait, TimeUnit unit) {
        if (this.consistency == ScanConsistency.NOT_BOUNDED) {
            this.scanWait = null;
        } else {
            this.scanWait = durationToN1qlFormat(wait, unit);
        }
        return this;
    }
}
