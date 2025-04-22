package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.query.dsl.path.Path;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface DropPath extends Path, Statement {

    UsingPath drop(String keyspace, String indexName);

    UsingPath drop(String namespace, String keyspace, String indexName);

    UsingPath dropPrimary(String keyspace);

    UsingPath dropPrimary(String namespace, String keyspace);
}
