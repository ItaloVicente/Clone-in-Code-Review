package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.path.Path;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface CreateIndexPath extends Path {

    OnPath create(String indexName);

    OnPrimaryPath createPrimary();
}
