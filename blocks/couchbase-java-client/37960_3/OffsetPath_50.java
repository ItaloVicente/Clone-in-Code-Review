package com.couchbase.client.java.query.dsl.path;

public interface NestPath extends KeysPath {

    KeysPath as(String alias);

}
