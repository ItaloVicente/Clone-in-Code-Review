package com.couchbase.client.java.query.dsl.path;


import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.query.dsl.Expression;
import com.couchbase.client.java.query.dsl.element.KeysElement;

import static com.couchbase.client.java.query.dsl.Expression.s;
import static com.couchbase.client.java.query.dsl.Expression.x;

public class DefaultUpdateUsePath extends DefaultUpdateSetOrUnsetPath implements UpdateUsePath {

  public DefaultUpdateUsePath(AbstractPath parent) {
    super(parent);
  }

  @Override
  public UpdateSetOrUnsetPath useKeys(Expression expression) {
    element(new KeysElement(KeysElement.ClauseType.USE_KEYSPACE, expression));
    return new DefaultUpdateSetOrUnsetPath(this);
  }

  @Override
  public UpdateSetOrUnsetPath useKeys(String key) {
    return useKeys(x(key));
  }

  @Override
  public UpdateSetOrUnsetPath useKeysValues(String... keys) {
    if (keys.length == 1) {
      return useKeys(s(keys[0]));
    }
    return useKeys(JsonArray.from((Object[]) keys));
  }

  @Override
  public UpdateSetOrUnsetPath useKeys(JsonArray keys) {
    return useKeys(x(keys));
  }
}
