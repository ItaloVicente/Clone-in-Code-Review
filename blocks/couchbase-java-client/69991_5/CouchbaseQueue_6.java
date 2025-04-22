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
public class CouchbaseQueue<E> {

    private final String docId;
    private final Bucket bucket;
    private final Class<E> type;

    public CouchbaseQueue(Bucket bucket, String docId, Class<E> type) {
        this.bucket = bucket;
        this.docId = docId;
        this.type = type;
        if (!bucket.exists(docId)) {
            bucket.insert(JsonArrayDocument.create(docId, JsonArray.create()));
        }
    }

    public boolean add(final E element) {
        return this.bucket.queuePush(this.docId, element);
    }

    public boolean add(final E element, final MutationOptionBuilder mutationOptionBuilder) {
        return this.bucket.queuePush(this.docId, element, mutationOptionBuilder);
    }

    public E remove() {
        return this.bucket.queuePop(this.docId, this.type);
    }

    public E remove(final MutationOptionBuilder mutationOptionBuilder) {
        return this.bucket.queuePop(this.docId, this.type, mutationOptionBuilder);
    }

    public int size() {
        return this.bucket.queueSize(this.docId);
    }
}
