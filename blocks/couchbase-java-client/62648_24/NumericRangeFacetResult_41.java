package com.couchbase.client.java.fts.result.facets;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NumericRange that = (NumericRange) o;

        if (count != that.count) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }
        if (min != null ? !min.equals(that.min) : that.min != null) {
            return false;
        }
        return max != null ? max.equals(that.max) : that.max == null;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (min != null ? min.hashCode() : 0);
        result = 31 * result + (max != null ? max.hashCode() : 0);
        result = 31 * result + (int) (count ^ (count >>> 32));
        return result;
    }
}
