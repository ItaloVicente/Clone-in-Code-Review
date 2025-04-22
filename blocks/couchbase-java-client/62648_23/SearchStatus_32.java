package com.couchbase.client.java.fts.result;

import java.util.List;
import java.util.Map;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.HighlightStyle;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.result.hits.HitLocations;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface SearchQueryRow {

    String index();

    String id();

    double score();

    JsonObject explanation();

    HitLocations locations();

    Map<String, List<String>> fragments();

    Map<String, String> fields();

}
