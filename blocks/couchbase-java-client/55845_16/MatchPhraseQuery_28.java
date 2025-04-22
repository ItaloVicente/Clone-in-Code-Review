
package com.couchbase.client.java.search.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class MatchPhraseQuery extends SearchQuery {
    private final String matchPhrase;
    private final String field;
    private final String analyzer;

    protected MatchPhraseQuery(Builder builder) {
        super(builder);
        matchPhrase = builder.matchPhrase;
        field = builder.field;
        analyzer = builder.analyzer;
    }

    public static Builder on(String index) {
        return new Builder(index);
    }

    public String match() {
        return matchPhrase;
    }

    public String field() {
        return field;
    }

    public String analyzer() {
        return analyzer;
    }

    @Override
    public JsonObject queryJson() {
        return JsonObject.create()
                .put("match_phrase", matchPhrase)
                .put("field", field)
                .put("analyzer", analyzer);
    }

    public static class Builder extends SearchQuery.Builder {
        private String matchPhrase;
        private String field;
        private String analyzer;

        protected Builder(String index) {
            super(index);
        }

        public MatchPhraseQuery build() {
            return new MatchPhraseQuery(this);
        }

        public Builder matchPhrase(String matchPhrase) {
            this.matchPhrase = matchPhrase;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public Builder analyzer(String analyzer) {
            this.analyzer = analyzer;
            return this;
        }
    }
}
