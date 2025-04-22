package com.couchbase.client.java.query.dsl.path;

public interface MergeUpdatePath extends MergeDeletePath {

  MergeUpdateSetOrUnsetPath whenMatchedThenUpdate();

}
