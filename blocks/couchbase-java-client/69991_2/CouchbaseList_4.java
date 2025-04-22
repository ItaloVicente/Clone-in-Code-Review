package com.couchbase.client.java.datastructures;

import com.couchbase.client.core.BackpressureException;
import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.RequestCancelledException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonArrayDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.error.*;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class CouchbaseList<E> {

	private final String docId;
	private final Bucket bucket;
	private final Class<E> type;

	public CouchbaseList(Bucket bucket, String docId, Class<E> type) {
		this.bucket = bucket;
		this.docId = docId;
		this.type = type;
		if (!bucket.exists(docId)) {
			bucket.insert(JsonArrayDocument.create(docId, JsonArray.create()));
		}
	}

	public E get(final int index) {
		return this.bucket.listGet(this.docId, index, this.type);
	}

	public boolean add(final E element) {
		return this.bucket.listAppend(this.docId, element);
	}

	public boolean add(final E element, final MutationOptionBuilder mutationOptionBuilder) {
		return this.bucket.listAppend(this.docId, mutationOptionBuilder);
	}

	public boolean remove(int index) {
		return this.bucket.listRemove(this.docId, index);
	}

	public boolean remove(final int index, final MutationOptionBuilder mutationOptionBuilder) {
		return this.bucket.listRemove(this.docId, index, mutationOptionBuilder);
	}

	public boolean set(int index, final E element) {
		return this.bucket.listSet(this.docId, index, element);
	}

	public boolean set(int index, final E element, MutationOptionBuilder mutationOptionBuilder) {
		return this.bucket.listSet(this.docId, index, element, mutationOptionBuilder);
	}

	public int size() {
		return this.bucket.listSize(this.docId);
	}
}
