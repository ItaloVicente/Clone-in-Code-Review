package com.couchbase.client.java.query.dsl.path;

public interface UnnestPath extends LetPath {

    LetPath as(String alias);

}
