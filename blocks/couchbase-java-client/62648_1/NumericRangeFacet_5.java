package com.couchbase.client.java.fts.facet;

public class NumericRange {

    private final String name;
    private final Double min;
    private final Double max;

    public NumericRange(String name, Double min, Double max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public static NumericRange nr(String name, Double min, Double max) {
        return new NumericRange(name, min, max);
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
}
