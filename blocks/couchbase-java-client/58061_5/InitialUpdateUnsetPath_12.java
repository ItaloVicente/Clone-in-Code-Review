package com.couchbase.client.java.query.dsl.path;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.query.dsl.Expression;

public interface DeleteUsePath extends MutateWherePath {

  MutateWherePath useKeys(Expression expression);

  MutateWherePath useKeys(String key);

  MutateWherePath useKeysValues(String... keys);

  MutateWherePath useKeys(JsonArray keys);

}
