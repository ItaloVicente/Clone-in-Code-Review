package com.couchbase.client.java.datastructures;

import com.couchbase.client.core.BackpressureException;
import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.RequestCancelledException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.*;
import com.couchbase.client.java.document.JsonArrayDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.error.*;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class CouchbaseSet<E> {

    private final String docId;
    private final Bucket bucket;
    private final Class<E> type;

    public CouchbaseSet(Bucket bucket, String docId, Class<E> type) {
        this.bucket = bucket;
        this.docId = docId;
        this.type = type;
        if (!bucket.exists(docId)) {
            bucket.insert(JsonArrayDocument.create(docId, JsonArray.create()));
        }
    }

    public Boolean add(final E element) {
        return this.bucket.setAdd(this.docId, element);
    }

    public Boolean add(final E element, MutationOptionBuilder mutationOptionBuilder) {
        return this.bucket.setAdd(this.docId, element, mutationOptionBuilder);
    }

    public Boolean exists(final E element) {
        return this.bucket.setContains(this.docId, element);
    }

    public E remove(E element) {
        return this.bucket.setRemove(this.docId, element);
    }

    public E remove(final E element, final MutationOptionBuilder mutationOptionBuilder) {
        return this.bucket.setRemove(this.docId, element, mutationOptionBuilder);
    }

    public int size() {
        return this.bucket.setSize(this.docId);
    }
}
