package com.couchbase.client.java.query.dsl.path;

import com.couchbase.client.java.query.dsl.element.LimitElement;

public class DefaultMutateLimitPath extends DefaultReturningPath implements MutateLimitPath {

    public DefaultMutateLimitPath(AbstractPath parent) {
        super(parent);
    }

    @Override
    public ReturningPath limit(int limit) {
        element(new LimitElement(limit));
        return new DefaultReturningPath(this);
    }
}
