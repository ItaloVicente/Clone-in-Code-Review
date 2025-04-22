package com.couchbase.client.java.fts.result.hits;


import java.util.Arrays;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class HitLocation {

    private final String field;
    private final String term;
    private final long pos;
    private final long start;
    private final long end;

    private final long[] arrayPositions;

    public HitLocation(String field, String term, long pos, long start, long end, long[] arrayPositions) {
        this.field = field;
        this.term = term;
        this.pos = pos;
        this.start = start;
        this.end = end;
        this.arrayPositions = arrayPositions;
    }

    public HitLocation(String field, String term, long pos, long start, long end) {
        this(field, term, pos, start, end, null);
    }

    public String field() {
        return field;
    }

    public String term() {
        return term;
    }

    public long pos() {
        return pos;
    }

    public long start() {
        return start;
    }

    public long end() {
        return end;
    }

    public long[] arrayPositions() {
        return arrayPositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HitLocation that = (HitLocation) o;

        if (pos != that.pos) {
            return false;
        }
        if (start != that.start) {
            return false;
        }
        if (end != that.end) {
            return false;
        }
        if (!field.equals(that.field)) {
            return false;
        }
        if (!term.equals(that.term)) {
            return false;
        }
        return Arrays.equals(arrayPositions, that.arrayPositions);

    }

    @Override
    public int hashCode() {
        int result = field.hashCode();
        result = 31 * result + term.hashCode();
        result = 31 * result + (int) (pos ^ (pos >>> 32));
        result = 31 * result + (int) (start ^ (start >>> 32));
        result = 31 * result + (int) (end ^ (end >>> 32));
        result = 31 * result + Arrays.hashCode(arrayPositions);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder()
                .append("HitLocation{")
                .append("field='").append(field).append('\'')
                .append(", term='").append(term).append('\'')
                .append(", pos=").append(pos)
                .append(", start=").append(start)
                .append(", end=").append(end);

        if (arrayPositions != null) {
            sb.append(", arrayPositions=").append(Arrays.toString(arrayPositions));
        }

        sb.append('}');
        return sb.toString();
    }
}
