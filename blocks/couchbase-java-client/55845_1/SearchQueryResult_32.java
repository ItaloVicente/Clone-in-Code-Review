
package com.couchbase.client.java.search;

import java.util.List;
import java.util.Map;

public class SearchQueryHit {
    private final String id;
    private final double score;
    private final String explanation;
    private final Map<String, Map<String, Location[]>> locations;
    private final Map<String, String[]> fragments;

    public SearchQueryHit(String id, double score, String explanation,
                          Map<String, Map<String, Location[]>> locations,
                          Map<String, String[]> fragments) {
        this.id = id;
        this.score = score;
        this.explanation = explanation;
        this.locations = locations;
        this.fragments = fragments;
    }

    @Override
    public String toString() {
        return "SearchQueryHit{id='" + id + "', score=" + score + '}';
    }

    public String id() {
        return id;
    }

    public double score() {
        return score;
    }

    public String explanation() {
        return explanation;
    }

    public Map<String, Map<String, Location[]>> locations() {
        return locations;
    }

    public Map<String, String[]> fragments() {
        return fragments;
    }

    public static class Location {
        private final double pos;
        private final double start;
        private final double end;
        private final double[] arrayPositions;

        public Location(double pos, double start, double end, double[] arrayPositions) {
            this.pos = pos;
            this.start = start;
            this.end = end;
            this.arrayPositions = arrayPositions;
        }
    }
}
