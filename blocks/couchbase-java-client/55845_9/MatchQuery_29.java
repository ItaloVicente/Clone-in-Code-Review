
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonObject;

public class MatchQuery extends SearchQuery {
    private static final int PREFIX_LENGTH = 0;
    private static final int FUZZINESS = 2;

    private final String match;
    private final String field;
    private final String analyzer;
    private final int prefixLength;
    private final int fuzziness;

    protected MatchQuery(Builder builder) {
        super(builder);
        match = builder.match;
        field = builder.field;
        analyzer = builder.analyzer;
        prefixLength = builder.prefixLength;
        fuzziness = builder.fuzziness;
    }

    public static Builder on(String index) {
        return new Builder(index);
    }

    public String match() {
        return match;
    }

    public String field() {
        return field;
    }

    public String analyzer() {
        return analyzer;
    }

    public int prefixLength() {
        return prefixLength;
    }

    public int fuzziness() {
        return fuzziness;
    }

    @Override
    public JsonObject queryJson() {
        return JsonObject.create()
                .put("match", match)
                .put("field", field)
                .put("analyzer", analyzer)
                .put("prefix_length", prefixLength)
                .put("fuzziness", fuzziness);
    }

    public static class Builder extends SearchQuery.Builder {
        private String match;
        private String field;
        private String analyzer;
        private int prefixLength = PREFIX_LENGTH;
        private int fuzziness = FUZZINESS;

        protected Builder(String index) {
            super(index);
        }

        public MatchQuery build() {
            return new MatchQuery(this);
        }

        public Builder match(String match) {
            this.match = match;
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

        public Builder fuzziness(int fuzziness) {
            this.fuzziness = fuzziness;
            return this;
        }

        public Builder prefixLength(int prefixLength) {
            this.prefixLength = prefixLength;
            return this;
        }
    }
}
