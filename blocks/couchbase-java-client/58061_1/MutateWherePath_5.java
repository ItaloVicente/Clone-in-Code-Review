package com.couchbase.client.java.query.dsl.path;

public interface MutateLimitPath extends ReturningPath {

    ReturningPath limit(int limit);

}
