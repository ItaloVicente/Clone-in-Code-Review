package com.couchbase.client.java.query.dsl.path;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.query.dsl.Expression;

public interface UpdateUsePath extends UpdateSetOrUnsetPath {

  UpdateSetOrUnsetPath useKeys(Expression expression);

  UpdateSetOrUnsetPath useKeys(String key);

  UpdateSetOrUnsetPath useKeysValues(String... keys);

  UpdateSetOrUnsetPath useKeys(JsonArray keys);

}
