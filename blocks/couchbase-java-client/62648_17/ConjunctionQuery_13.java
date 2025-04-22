package com.couchbase.client.java.fts.queries;

import com.couchbase.client.java.document.json.JsonObject;

public class BooleanQuery extends AbstractFtsQuery {

    private final ConjunctionQuery must;
    private final DisjunctionQuery mustNot;
    private final DisjunctionQuery should;

    public BooleanQuery() {
        super();
        this.must = new ConjunctionQuery();
        this.should = new DisjunctionQuery();
        this.mustNot = new DisjunctionQuery();
    }

    public BooleanQuery shouldMin(int minForShould) {
        this.should.min(minForShould);
        return this;
    }

    public BooleanQuery must(AbstractFtsQuery... mustQueries) {
        must.and(mustQueries);
        return this;
    }

    public BooleanQuery mustNot(AbstractFtsQuery... mustNotQueries) {
        mustNot.or(mustNotQueries);
        return this;
    }
    public BooleanQuery should(AbstractFtsQuery... shouldQueries) {
        should.or(shouldQueries);
        return this;
    }

    @Override
    public BooleanQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        boolean mustIsEmpty = must == null || must.childQueries().isEmpty();
        boolean mustNotIsEmpty = mustNot == null || mustNot.childQueries().isEmpty();
        boolean shouldIsEmpty = should == null || should.childQueries().isEmpty();

        if (mustIsEmpty && mustNotIsEmpty && shouldIsEmpty) {
            throw new IllegalArgumentException("Boolean query needs at least one of must, mustNot and should");
        }

        if (!mustIsEmpty) {
            JsonObject jsonMust = JsonObject.create();
            must.injectParamsAndBoost(jsonMust);
            input.put("must", jsonMust);
        }

        if (!mustNotIsEmpty) {
            JsonObject jsonMustNot = JsonObject.create();
            mustNot.injectParamsAndBoost(jsonMustNot);
            input.put("must_not", jsonMustNot);
        }

        if (!shouldIsEmpty) {
            JsonObject jsonShould = JsonObject.create();
            should.injectParamsAndBoost(jsonShould);
            input.put("should", jsonShould);
        }
    }
}
