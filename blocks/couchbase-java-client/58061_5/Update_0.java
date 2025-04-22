package com.couchbase.client.java.query;

import com.couchbase.client.java.CouchbaseAsyncBucket;
import com.couchbase.client.java.query.dsl.Expression;
import com.couchbase.client.java.query.dsl.element.Element;
import com.couchbase.client.java.query.dsl.path.AbstractPath;
import com.couchbase.client.java.query.dsl.path.DefaultDeleteUsePath;
import com.couchbase.client.java.query.dsl.path.DeleteUsePath;

import static com.couchbase.client.java.query.dsl.Expression.i;
import static com.couchbase.client.java.query.dsl.Expression.x;

public class Delete {

  private Delete() {}

  public static DeleteUsePath deleteFrom(String bucket) {
    return deleteFrom(i(bucket));
  }

  public static DeleteUsePath deleteFrom(Expression bucket) {
    return new DefaultDeleteUsePath(new DeletePath(bucket));
  }

  public static DeleteUsePath deleteFromCurrentBucket() {
    return deleteFrom(x(CouchbaseAsyncBucket.CURRENT_BUCKET_IDENTIFIER));
  }

  private static class DeletePath extends AbstractPath {
    public DeletePath(final Expression bucket) {
      super(null);
      element(new Element() {
        @Override
        public String export() {
          return "DELETE FROM " + bucket.toString();
        }
      });
    }
  }

}
