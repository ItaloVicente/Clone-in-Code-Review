package com.couchbase.client.java.fts.result.facets;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public abstract class AbstractFacetResult implements FacetResult {

    protected final String name;
    protected final String field;
    protected final long total;
    protected final long missing;
    protected final long other;

    protected AbstractFacetResult(String name, String field, long total, long missing, long other) {
        this.name = name;
        this.field = field;
        this.total = total;
        this.missing = missing;
        this.other = other;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String field() {
        return this.field;
    }

    @Override
    public long missing() {
        return this.missing;
    }

    @Override
    public long other() {
        return this.other;
    }

    @Override
    public long total() {
        return this.total;
    }
}
