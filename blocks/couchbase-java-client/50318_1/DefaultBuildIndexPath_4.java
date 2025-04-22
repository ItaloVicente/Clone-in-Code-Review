package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.path.Path;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface BuildIndexPath extends Path {

    IndexNamesPath on(String namespace, String keyspace);

    IndexNamesPath on(String keyspace);
}
