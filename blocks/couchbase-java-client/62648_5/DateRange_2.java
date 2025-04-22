package com.couchbase.client.java.fts;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.queries.FuzzyQuery;
import com.couchbase.client.java.fts.queries.MatchPhraseQuery;
import com.couchbase.client.java.fts.queries.MatchQuery;
import com.couchbase.client.java.fts.queries.StringQuery;

public abstract class SearchQuery {

    private final SearchParams searchParams;
    private Double boost;

    protected SearchQuery(final SearchParams searchParams) {
        this.searchParams = searchParams;
    }

    public static StringQuery string(String query) {
        return string(query, SearchParams.build());
    }

    public static StringQuery string(String query, SearchParams searchParams) {
        return new StringQuery(query, searchParams);
    }

    public static MatchQuery match(String match) {
        return match(match, SearchParams.build());
    }

    public static MatchQuery match(String match, SearchParams searchParams) {
        return new MatchQuery(match, searchParams);
    }

    public static MatchPhraseQuery matchPhrase(String matchPhrase) {
        return matchPhrase(matchPhrase, SearchParams.build());
    }

    public static MatchPhraseQuery matchPhrase(String matchPhrase, SearchParams searchParams) {
        return new MatchPhraseQuery(matchPhrase, searchParams);
    }

    public static FuzzyQuery fuzzy(String term) {
        return fuzzy(term, SearchParams.build());
    }

    public static FuzzyQuery fuzzy(String term, SearchParams searchParams) {
        return new FuzzyQuery(term, searchParams);
    }

    public SearchQuery boost(double boost) {
        this.boost = boost;
        return this;
    }

    public JsonObject export() {
        JsonObject result = JsonObject.create();
        searchParams.injectParams(result);
        JsonObject query = JsonObject.create();
        injectParams(query);
        return result.put("query", query);
    }

    protected void injectParams(JsonObject input) {
        if (boost != null) {
            input.put("boost", boost);
        }
    }

    @Override
    public String toString() {
        return export().toString();
    }

}
