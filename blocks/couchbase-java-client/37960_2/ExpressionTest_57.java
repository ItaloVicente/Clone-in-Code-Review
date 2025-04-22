package com.couchbase.client.java.query.dsl.path;

import com.couchbase.client.java.query.dsl.Expression;

public interface WherePath extends GroupByPath {

    GroupByPath where(Expression expression);

}
