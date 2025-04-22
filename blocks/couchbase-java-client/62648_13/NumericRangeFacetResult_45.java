package com.couchbase.client.java.fts.result.facets;

public class NumericRange {

    private final String name;
    private final Double min;
    private final Double max;
    private final long count;

    public NumericRange(String name, Double min, Double max, long count) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.count = count;
    }

    public String name() {
        return name;
    }

    public Double min() {
        return min;
    }

    public Double max() {
        return max;
    }

    public long count() {
        return count;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("name='").append(name).append('\'');
        if (min != null) {
            sb.append(", min=").append(min);
        }
        if (max != null) {
            sb.append(", max=").append(max);
        }
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
