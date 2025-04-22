package com.couchbase.client.java.fts.queries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class PhraseQuery extends AbstractFtsQuery {

    private final List<String> terms;
    private String field;

    public PhraseQuery(String... terms) {
        super();
        this.terms = new LinkedList<String>();
        Collections.addAll(this.terms, terms);
    }

    public PhraseQuery field(String field) {
        this.field = field;
        return this;
    }

    @Override
    public PhraseQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        if (terms.isEmpty()) {
            throw new IllegalArgumentException("Phrase query must at least have one term");
        }
        JsonArray terms = JsonArray.from(this.terms);
        input.put("terms", terms);

        if (field != null) {
            input.put("field", this.field);
        }
    }
}
