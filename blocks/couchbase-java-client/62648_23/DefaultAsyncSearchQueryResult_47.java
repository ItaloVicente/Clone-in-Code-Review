package com.couchbase.client.java.fts.result.hits;

import java.util.List;
import java.util.Set;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface HitLocations {

    HitLocations add(HitLocation l);

    List<HitLocation> get(String field);

    List<HitLocation> get(String field, String term);

    List<HitLocation> getAll();

    long count();

    List<String> fields();

    List<String> termsFor(String field);

    Set<String> terms();
}
