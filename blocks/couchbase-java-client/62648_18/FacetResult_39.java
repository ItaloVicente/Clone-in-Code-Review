package com.couchbase.client.java.fts.result.facets;

import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class DefaultTermFacetResult extends AbstractFacetResult implements TermFacetResult {

    private final List<TermRange> terms;

    public DefaultTermFacetResult(String name, String field, long total, long missing, long other,
            List<TermRange> terms) {
        super(name, field, total, missing, other);
        this.terms = terms;
    }

    @Override
    public List<TermRange> terms() {
        return this.terms;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TermFacetResult{")
                .append("name='").append(name).append('\'')
                .append(", field='").append(field).append('\'')
                .append(", total=").append(total)
                .append(", missing=").append(missing)
                .append(", other=").append(other)
                .append(", terms=").append(terms)
                .append('}');
        return sb.toString();
    }
}
