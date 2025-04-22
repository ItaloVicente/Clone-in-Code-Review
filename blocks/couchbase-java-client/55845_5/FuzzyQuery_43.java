
package com.couchbase.client.java.search.query;

import com.couchbase.client.java.document.json.JsonObject;

public class FuzzyQuery extends SearchQuery {
    public static final double BOOST = 1.0;
    private static final int PREFIX_LENGTH = 0;
    private static final int FUZZINESS = 2;

    private final String term;
    private final String field;
    private final int prefixLength;
    private final double boost;
    private final int fuzziness;

    protected FuzzyQuery(Builder builder) {
        super(builder);
        term = builder.term;
        prefixLength = builder.prefixLength;
        fuzziness = builder.fuzziness;
        field = builder.field;
        boost = builder.boost;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String term() {
        return term;
    }

    public String field() {
        return field;
    }

    public double prefixLength() {
        return prefixLength;
    }

    public double boost() {
        return boost;
    }

    @Override
    public JsonObject queryJson() {
        return JsonObject.create()
                .put("term", term)
                .put("field", field)
                .put("prefix_length", prefixLength)
                .put("fuzziness", fuzziness)
                .put("boost", boost);
    }

    public static class Builder extends SearchQuery.Builder {
        public double boost = BOOST;
        private String term;
        private String field;
        public int prefixLength = PREFIX_LENGTH;
        public int fuzziness = FUZZINESS;

        public FuzzyQuery build() {
            return new FuzzyQuery(this);
        }

        public Builder boost(double boost) {
            this.boost = boost;
            return this;
        }

        public Builder fuzziness(int fuzziness) {
            this.fuzziness = fuzziness;
            return this;
        }

        public Builder term(String term) {
            this.term = term;
            return this;
        }

        public Builder prefixLength(int prefixLength) {
            this.prefixLength = prefixLength;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }
    }
}
