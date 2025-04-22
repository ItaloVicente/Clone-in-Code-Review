package com.couchbase.client.java.fts.result.hits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class DefaultHitLocations implements HitLocations {

    private final Map<String, Map<String, List<HitLocation>>> locations = new HashMap<String, Map<String, List<HitLocation>>>();
    private volatile int size;

    @Override
    public HitLocations add(HitLocation l) {
        Map<String, List<HitLocation>> byTerm = locations.get(l.field());
        if (byTerm == null) {
            byTerm = new HashMap<String, List<HitLocation>>();
            locations.put(l.field(), byTerm);
        }

        List<HitLocation> list = byTerm.get(l.term());
        if (list == null) {
            list = new ArrayList<HitLocation>();
            byTerm.put(l.term(), list);
        }

        list.add(l);
        size++;
        return this;
    }

    @Override
    public List<HitLocation> get(String field) {
        Map<String, List<HitLocation>> byTerm = locations.get(field);
        if (byTerm == null) {
            return Collections.emptyList();
        }

        List<HitLocation> result = new LinkedList<HitLocation>();
        for (List<HitLocation> termList : byTerm.values()) {
            result.addAll(termList);
        }
        return result;
    }

    @Override
    public List<HitLocation> get(String field, String term) {
        Map<String, List<HitLocation>> byTerm = locations.get(field);
        if (byTerm == null) {
            return Collections.emptyList();
        }

        List<HitLocation> result = byTerm.get(term);
        if (result == null) {
            return Collections.emptyList();
        }
        return new ArrayList<HitLocation>(result);
    }

    @Override
    public List<HitLocation> getAll() {
        List<HitLocation> all = new LinkedList<HitLocation>();
        for (Map.Entry<String, Map<String, List<HitLocation>>> terms : locations.entrySet()) {
            for (List<HitLocation> hitLocations : terms.getValue().values()) {
                all.addAll(hitLocations);
            }
        }
        return all;
    }

    @Override
    public long count() {
        return size;
    }

    @Override
    public List<String> fields() {
        return new ArrayList(locations.keySet());
    }

    @Override
    public List<String> termsFor(String field) {
        final Map<String, List<HitLocation>> termMap = locations.get(field);
        if (termMap == null) {
            return Collections.emptyList();
        }
        return new ArrayList<String>(termMap.keySet());
    }

    @Override
    public Set<String> terms() {
        Set<String> termSet = new HashSet<String>();
        for (Map<String,List<HitLocation>> termMap : locations.values()) {
            termSet.addAll(termMap.keySet());
        }
        return termSet;
    }

    public static HitLocations from(JsonObject locationsJson) {
        DefaultHitLocations hitLocations = new DefaultHitLocations();
        if (locationsJson == null) {
            return hitLocations;
        }

        for (String field : locationsJson.getNames()) {
            JsonObject termsJson = locationsJson.getObject(field);

            for (String term : termsJson.getNames()) {
                JsonArray locsJson = termsJson.getArray(term);

                for (int i = 0; i < locsJson.size(); i++) {
                    JsonObject loc = locsJson.getObject(i);
                    long pos = loc.getLong("pos");
                    long start = loc.getLong("start");
                    long end = loc.getLong("end");
                    JsonArray arrayPositionsJson = loc.getArray("array_positions");
                    long[] arrayPositions = null;
                    if (arrayPositionsJson != null) {
                        arrayPositions = new long[arrayPositionsJson.size()];
                        for (int j = 0; j < arrayPositionsJson.size(); j++) {
                            arrayPositions[j] = arrayPositionsJson.getLong(j);
                        }
                    }
                    hitLocations.add(new HitLocation(field, term, pos, start, end, arrayPositions));
                }
            }
        }
        return hitLocations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DefaultHitLocations{")
                .append("size=").append(size)
                .append(", locations=[");

        for (Map<String, List<HitLocation>> map : locations.values()) {
            for (List<HitLocation> hitLocations : map.values()) {
                for (HitLocation hitLocation : hitLocations) {
                    sb.append(hitLocation).append(",");
                }
            }
        }

        if (!locations.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]}");
        return sb.toString();
    }
}
