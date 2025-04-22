package com.couchbase.client.java.query.dsl.path;

public class DefaultSelectResultPath extends DefaultOrderByPath implements SelectResultPath {

    public DefaultSelectResultPath(AbstractPath parent) {
        super(parent);
    }

    @Override
    public SelectPath union() {
        return null;
    }

    @Override
    public SelectPath unionAll() {
        return null;
    }

}
