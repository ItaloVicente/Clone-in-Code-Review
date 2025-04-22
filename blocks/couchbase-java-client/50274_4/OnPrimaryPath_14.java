package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;
import com.couchbase.client.java.query.dsl.path.Path;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface OnPath extends Path {

    UsingWherePath on(String keyspace, Expression expression, Expression... additionalExpressions);

    UsingWherePath on(String namespace, String keyspace, Expression expression, Expression... additionalExpressions);



}
