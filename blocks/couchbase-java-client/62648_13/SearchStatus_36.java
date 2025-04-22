package com.couchbase.client.java.fts.result;

import java.util.List;
import java.util.Map;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.result.hits.HitLocations;

public interface SearchQueryRow {

    String index();
    String id();
    double score();
    JsonObject explanation();
    HitLocations locations();
    Map<String, List<String>> fragments();
    Map<String, String> fields();

}
