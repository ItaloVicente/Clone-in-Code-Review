package com.couchbase.client.java.query.dsl.path;


import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.query.dsl.Expression;
import com.couchbase.client.java.query.dsl.element.KeysElement;

import static com.couchbase.client.java.query.dsl.Expression.s;
import static com.couchbase.client.java.query.dsl.Expression.x;

public class DefaultDeleteUsePath extends DefaultMutateWherePath implements DeleteUsePath {

  public DefaultDeleteUsePath(AbstractPath parent) {
    super(parent);
  }

  @Override
  public MutateWherePath useKeys(Expression expression) {
    element(new KeysElement(KeysElement.ClauseType.USE_KEYSPACE, expression));
    return new DefaultMutateWherePath(this);
  }

  @Override
  public MutateWherePath useKeys(String key) {
    return useKeys(x(key));
  }

  @Override
  public MutateWherePath useKeysValues(String... keys) {
    if (keys.length == 1) {
      return useKeys(s(keys[0]));
    }
    return useKeys(JsonArray.from((Object[]) keys));
  }

  @Override
  public MutateWherePath useKeys(JsonArray keys) {
    return useKeys(x(keys));
  }
}
