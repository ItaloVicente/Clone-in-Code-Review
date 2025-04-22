package com.couchbase.client.java.query.dsl.path.index;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.element.BuildIndexElement;
import com.couchbase.client.java.query.dsl.path.AbstractPath;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class DefaultBuildIndexPath extends AbstractPath implements BuildIndexPath {

    public DefaultBuildIndexPath() {
        super(null);
    }

    @Override
    public IndexNamesPath on(String namespace, String keyspace) {
        element(new BuildIndexElement(namespace, keyspace));
        return new DefaultIndexNamesPath(this);
    }

    @Override
    public IndexNamesPath on(String keyspace) {
        element(new BuildIndexElement(null, keyspace));
        return new DefaultIndexNamesPath(this);
    }
}
