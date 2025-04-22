package com.couchbase.client.java.query.dsl.path;

public interface SelectResultPath extends OrderByPath {

    SelectPath union();

    SelectPath unionAll();
}
