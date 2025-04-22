package com.couchbase.client.java.query.dsl.path;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.element.HintIndexElement;
import com.couchbase.client.java.query.dsl.path.index.IndexReference;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class DefaultHintPath extends DefaultKeysPath implements HintPath {

    public DefaultHintPath(AbstractPath parent) {
        super(parent);
    }

    @Override
    public KeysPath useIndex(IndexReference... indexes) {
        element(new HintIndexElement(indexes));
        return new DefaultKeysPath(this);
    }

    @Override
    public KeysPath useIndex(String... indexes) {
        IndexReference[] indexRefs = new IndexReference[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            indexRefs[i] = IndexReference.indexRef(indexes[i]);
        }
        return useIndex(indexRefs);
    }
}
