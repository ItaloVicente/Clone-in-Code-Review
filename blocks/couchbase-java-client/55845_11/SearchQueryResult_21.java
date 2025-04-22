
package com.couchbase.client.java.search;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class SearchQueryHit {
    private final String index;
    private final String id;
    private final double score;
    private final String explanation;
    private final Map<String, Map<String, Location[]>> locations;
    private final Map<String, String[]> fragments;
    private final Map<String, Object> fields;

    public SearchQueryHit(String index, String id, double score, String explanation,
                          Map<String, Map<String, Location[]>> locations,
                          Map<String, String[]> fragments,
                          Map<String, Object> fields) {
        this.index = index;
        this.id = id;
        this.score = score;
        this.explanation = explanation;
        this.locations = locations;
        this.fragments = fragments;
        this.fields = fields;
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

    public String index() {
        return index;
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

    public Map<String, Object> fields() {
        return fields;
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
