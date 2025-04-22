package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class TermQuery extends SearchQuery {

    private final String term;
    private String field;
    private int fuzziness;
    private int prefixLength;

    public TermQuery(String term) {
        super();
        this.term = term;
    }

    public TermQuery field(String fieldName) {
        this.field = fieldName;
        return this;
    }

    public TermQuery fuzziness(int fuzziness) {
        this.fuzziness = fuzziness;
        return this;
    }

    public TermQuery prefixLength(int prefixLength) {
        this.prefixLength = prefixLength;
        return this;
    }

    @Override
    public TermQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        input.put("term", term);
        if (field != null) {
            input.put("field", field);
        }
        if (fuzziness > 0) {
            input.put("fuzziness", fuzziness);
            if (prefixLength > 0) {
                input.put("prefix_length", prefixLength);
            }
        }
    }
}
