package com.couchbase.client.java.fts.facet;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class TermFacet extends SearchFacet {

    TermFacet(String name, String field, int limit) {
        super(name, field, limit);
    }

}
