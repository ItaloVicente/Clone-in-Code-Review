package com.couchbase.client.java.query.dsl.path;

public interface AsPath extends KeysPath {

    KeysPath as(String alias);

}
