package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.Index;
import com.couchbase.client.java.query.dsl.path.Path;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface IndexNamesPath extends Path {

    UsingPath indexes(String indexName, String... indexNames);

    UsingPath primary();
}
