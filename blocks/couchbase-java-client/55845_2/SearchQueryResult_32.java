
package com.couchbase.client.java.search;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

import java.util.ArrayList;
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
        StringBuilder sb = new StringBuilder();
        sb.append("SearchQueryHit{id='" + id + "', score=" + score + ", fragments={");
        if (fragments != null) {
            List<String> entries = new ArrayList<String>(fragments.size());
            for (Map.Entry<String, String[]> fragment : fragments.entrySet()) {
                entries.add("\"" + fragment.getKey() + "\":" + JsonArray.from(fragment.getValue()).toString());
            }
            sb.append(String.join(", ", entries));
        }
        return sb.append("}}").toString();
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
