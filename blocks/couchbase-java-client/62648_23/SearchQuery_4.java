package com.couchbase.client.java.fts;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.fts.facet.SearchFacet;
import com.couchbase.client.java.fts.result.SearchQueryResult;
import com.couchbase.client.java.fts.result.SearchQueryRow;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SearchParams {

    private Integer limit;
    private Integer skip;
    private Boolean explain;
    private HighlightStyle highlightStyle;
    private String[] highlightFields;
    private String[] fields;
    private Map<String, SearchFacet> facets;
    private Long serverSideTimeout;

    private SearchParams() {
        this.highlightFields = new String[0];
        this.fields = new String[0];
        this.facets = new HashMap<String, SearchFacet>();
    }

    public static SearchParams build() {
        return new SearchParams();
    }

    public SearchParams limit(int limit) {
        this.limit = limit;
        return this;
    }

    public SearchParams skip(int skip) {
        this.skip = skip;
        return this;
    }

    public SearchParams explain() {
        return explain(true);
    }

    public SearchParams explain(boolean explain) {
        this.explain = explain;
        return this;
    }

    public SearchParams highlight(HighlightStyle style, String... fields) {
        this.highlightStyle = style;
        if (fields != null && fields.length > 0) {
            highlightFields = fields;
        }
        return this;
    }

    public SearchParams clearHighlight() {
        this.highlightStyle = null;
        this.highlightFields = null;
        return this;
    }

    public SearchParams fields(String... fields) {
        if (fields != null) {
            this.fields = fields;
        }
        return this;
    }

    public SearchParams addFacets(SearchFacet... facets) {
        if (facets != null) {
            for (SearchFacet facet : facets) {
                this.facets.put(facet.name(), facet);
            }
        }
        return this;
    }

    public SearchParams clearFacets() {
        this.facets.clear();
        return this;
    }

    public SearchParams serverSideTimeout(long timeout, TimeUnit unit) {
        this.serverSideTimeout = unit.toMillis(timeout);
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getSkip() {
        return skip;
    }

    public HighlightStyle getHighlightStyle() {
        return highlightStyle;
    }

    public String[] getHighlightFields() {
        return highlightFields;
    }

    public String[] getFields() {
        return fields;
    }

    public Map<String, SearchFacet> getFacets() {
        return facets;
    }

    public Long getServerSideTimeout() {
        return serverSideTimeout;
    }

    public void injectParams(JsonObject queryJson) {
        if (limit != null && limit >= 0) {
            queryJson.put("size", limit);
        }
        if (skip != null && skip >= 0) {
            queryJson.put("from", skip);
        }
        if (explain != null) {
            queryJson.put("explain", explain);
        }
        if (highlightStyle != null) {
            JsonObject highlight = JsonObject.create();
            highlight.put("style", highlightStyle.name().toLowerCase());
            if (highlightFields != null && highlightFields.length > 0) {
                highlight.put("fields", JsonArray.from(highlightFields));
            }
            queryJson.put("highlight", highlight);
        }
        if (fields != null && fields.length > 0) {
            queryJson.put("fields", JsonArray.from(fields));
        }
        if (!this.facets.isEmpty()) {
            JsonObject facets = JsonObject.create();
            for (SearchFacet f : this.facets.values()) {
                JsonObject facet = JsonObject.create();
                f.injectParams(facet);
                facets.put(f.name(), facet);
            }
            queryJson.put("facets", facets);
        }
        if(serverSideTimeout != null) {
            JsonObject control = JsonObject.empty();
            control.put("timeout", serverSideTimeout);
            queryJson.put("ctl", control);
        }
    }

}
