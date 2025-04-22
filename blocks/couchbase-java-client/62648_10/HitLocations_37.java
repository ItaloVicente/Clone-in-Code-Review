package com.couchbase.client.java.fts.result.hits;


class HitLocation {

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
}
