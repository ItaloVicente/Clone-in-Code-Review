package com.couchbase.client.java.fts.queries;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class MatchPhraseQuery extends AbstractFtsQuery {

    private final String matchPhrase;
    private String field;
    private String analyzer;

    public MatchPhraseQuery(String matchPhrase) {
        super();
        this.matchPhrase = matchPhrase;
    }

    @Override
    public MatchPhraseQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    public MatchPhraseQuery field(String field) {
        this.field = field;
        return this;
    }

    public MatchPhraseQuery analyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        input.put("match_phrase", matchPhrase);
        if (field != null) {
            input.put("field", field);
        }
        if (analyzer != null) {
            input.put("analyzer", analyzer);
        }
    }
}
