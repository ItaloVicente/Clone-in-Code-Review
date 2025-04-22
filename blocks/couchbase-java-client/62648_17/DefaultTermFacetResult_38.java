package com.couchbase.client.java.fts.result.facets;

import java.util.List;

public class DefaultNumericRangeFacetResult extends AbstractFacetResult implements NumericRangeFacetResult {

    private final List<NumericRange> numericRanges;

    public DefaultNumericRangeFacetResult(String name, String field, long total, long missing, long other,
            List<NumericRange> numericRanges) {
        super(name, field, total, missing, other);
        this.numericRanges = numericRanges;
    }

    @Override
    public List<NumericRange> numericRanges() {
        return this.numericRanges;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NumericRangeFacetResult{")
                .append("name='").append(name).append('\'')
                .append(", field='").append(field).append('\'')
                .append(", total=").append(total)
                .append(", missing=").append(missing)
                .append(", other=").append(other)
                .append(", ranges=").append(numericRanges)
                .append('}');
        return sb.toString();
    }
}
