package com.couchbase.client.java.query.dsl.path;

import com.couchbase.client.java.query.dsl.element.AsElement;

public class DefaultNestPath extends DefaultKeysPath implements NestPath {

    public DefaultNestPath(AbstractPath parent) {
        super(parent);
    }

    @Override
    public KeysPath as(String alias) {
        element(new AsElement(alias));
        return new DefaultKeysPath(this);
    }
}
