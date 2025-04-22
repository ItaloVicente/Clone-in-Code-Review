package com.couchbase.client.java.fts.result.facets;

import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class DefaultDateRangeFacetResult extends  AbstractFacetResult implements DateRangeFacetResult {

    private final List<DateRange> dateRanges;

    public DefaultDateRangeFacetResult(String name, String field, long total, long missing, long other,
            List<DateRange> dateRanges) {
        super(name, field, total, missing, other);
        this.dateRanges = dateRanges;
    }

    @Override
    public List<DateRange> dateRanges() {
        return this.dateRanges;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DateRangeFacetResult{")
                .append("name='").append(name).append('\'')
                .append(", field='").append(field).append('\'')
                .append(", total=").append(total)
                .append(", missing=").append(missing)
                .append(", other=").append(other)
                .append(", ranges=").append(dateRanges)
                .append('}');
        return sb.toString();
    }
}
