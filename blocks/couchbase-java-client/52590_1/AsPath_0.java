package com.couchbase.client.java.query.dsl.element;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.path.index.IndexReference;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class HintIndexElement implements Element {

    private final IndexReference[] indexReferences;

    public HintIndexElement(IndexReference... indexReferences) {
        this.indexReferences = indexReferences;
    }

    @Override
    public String export() {
        if (indexReferences == null || indexReferences.length < 1) {
            return "";
        }
        StringBuilder n1ql = new StringBuilder("USE INDEX (");
        for (IndexReference indexReference : indexReferences) {
            n1ql.append(indexReference.toString()).append(',');
        }
        n1ql.deleteCharAt(n1ql.length() - 1);
        n1ql.append(')');

        return n1ql.toString();
    }
}
