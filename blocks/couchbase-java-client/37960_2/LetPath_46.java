package com.couchbase.client.java.query.dsl.path;

import com.couchbase.client.java.query.dsl.Expression;

public interface KeysPath extends LetPath {

    LetPath keys(Expression expression);

}
