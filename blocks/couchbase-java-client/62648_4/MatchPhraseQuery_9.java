package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class FuzzyQuery extends SearchQuery {

    private final String term;
    private String field;
    private Integer prefixLength;
    private Integer fuzziness;

    public FuzzyQuery(String term, SearchParams searchParams) {
        super(searchParams);
        this.term = term;
    }

    @Override
    public FuzzyQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    public FuzzyQuery field(String field) {
        this.field = field;
        return this;
    }

    public FuzzyQuery prefixLength(int prefixLength) {
        this.prefixLength = prefixLength;
        return this;
    }

    public FuzzyQuery fuzziness(int fuzziness) {
        this.fuzziness = fuzziness;
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        super.injectParams(input);

        input.put("term", term);
        if (field != null) {
            input.put("field", field);
        }
        if (prefixLength != null) {
            input.put("prefix_length", prefixLength);
        }
        if (fuzziness != null) {
            input.put("fuzziness", fuzziness);
        }
    }
}
