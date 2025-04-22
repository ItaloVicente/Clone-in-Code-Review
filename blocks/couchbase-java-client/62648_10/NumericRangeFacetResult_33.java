package com.couchbase.client.java.fts.result.facets;

public class NumericRange {

    private final String name;
    private final double min;
    private final double max;
    private final long count;

    public NumericRange(String name, double min, double max, long count) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.count = count;
    }

    public String name() {
        return name;
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public long count() {
        return count;
    }

}
