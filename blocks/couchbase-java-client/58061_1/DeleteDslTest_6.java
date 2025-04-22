package com.couchbase.client.java.query.dsl.path;

import com.couchbase.client.java.query.dsl.Expression;

public interface MutateWherePath extends MutateLimitPath {

    MutateLimitPath where(Expression expression);

    MutateLimitPath where(String expression);

}
