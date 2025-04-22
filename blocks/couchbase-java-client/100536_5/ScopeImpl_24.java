package com.couchbase.client.java.internal;
import java.util.HashMap;
import java.util.Map;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.ReactiveCollection;
import com.couchbase.client.java.Scope;

public class ScopeImpl extends Scope {

    protected Map<String, Collection> collections;

    public ScopeImpl(String name, Bucket bucket) {
        super(name, bucket);
        collections = new HashMap<String, Collection>();
    }

    public Collection collection(String name) {
        throw new UnsupportedOperationException();
    }

    private Collection createCollection(String name, long collectionId) {
        Collection collection = new CollectionImpl(name, collectionId, this, this.bucket);
        collection.withOperationTimeout(this.timeout, this.timeoutUnit).withDurabilityOptions(this.durabilityOptions);
        collections.put(name, collection);
        return collection;
    }

    @Override
    public Collection collection(String name, long collectionId) {
        if (collections.containsKey(name)) {
            return collections.get(name);
        }
        return createCollection(name, collectionId);
    }

    @Override
    public ReactiveCollection reactiveCollection(String name) {
        throw new UnsupportedOperationException();
    }


    @Override
    public ReactiveCollection reactiveCollection(String name, long collectionId) {
        if (collections.containsKey(name)) {
            return collections.get(name).reactiveCollection();
        }
        return createCollection(name, collectionId).reactiveCollection();
    }

    @Override
    public void closeCollection(String name) {
        collections.remove(name);
    }
}
