package com.couchbase.client.java.query.dsl.path;

public interface JoinPath extends KeysPath {

    KeysPath as(String alias);

}
