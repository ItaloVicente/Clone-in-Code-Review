package com.couchbase.client.java.fts.queries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public abstract class AbstractCompoundQuery extends AbstractFtsQuery {

    List<AbstractFtsQuery> childQueries = new LinkedList<AbstractFtsQuery>();

    protected AbstractCompoundQuery(AbstractFtsQuery... queries) {
        super();
        addAll(queries);
    }

    protected void addAll(AbstractFtsQuery... queries) {
        Collections.addAll(childQueries, queries);
    }

    public List<AbstractFtsQuery> childQueries() {
        return this.childQueries;
    }
}
