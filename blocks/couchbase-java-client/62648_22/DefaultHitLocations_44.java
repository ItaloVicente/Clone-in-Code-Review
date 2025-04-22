package com.couchbase.client.java.fts.result.facets;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class TermRange {

    private final String name;
    private final long count;

    public TermRange(String name, long count) {
        this.name = name;
        this.count = count;
    }

    public String name() {
        return name;
    }

    public long count() {
        return count;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("name='").append(name).append('\'');
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

        TermRange termRange = (TermRange) o;

        if (count != termRange.count) {
            return false;
        }
        return name.equals(termRange.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (int) (count ^ (count >>> 32));
        return result;
    }
}
