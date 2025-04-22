package com.couchbase.client.java.datastructures.collections;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Queue;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.datastructures.collections.iterators.JsonArrayDocumentIterator;
import com.couchbase.client.java.document.JsonArrayDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.json.JsonValue;
import com.couchbase.client.java.error.CASMismatchException;
import com.couchbase.client.java.error.DocumentAlreadyExistsException;
import com.couchbase.client.java.error.subdoc.MultiMutationException;
import com.couchbase.client.java.subdoc.DocumentFragment;


@InterfaceStability.Experimental
@InterfaceAudience.Public
public class CouchbaseQueue<E> extends AbstractQueue<E> {

	private static final int MAX_OPTIMISTIC_LOCKING_ATTEMPTS = Integer.parseInt(System.getProperty("com.couchbase.datastructureCASRetryLimit", "10"));
	private final String id;
	private final Bucket bucket;

	public CouchbaseQueue(String id, Bucket bucket) {
		this.bucket = bucket;
		this.id = id;

		try {
			bucket.insert(JsonArrayDocument.create(id, JsonArray.empty()));
		} catch (DocumentAlreadyExistsException ex) {
		}
	}

	public CouchbaseQueue(String id, Bucket bucket, E... content) {
		this.bucket = bucket;
		this.id = id;

		JsonArray array = JsonArray.create();
		for (E e : content) {
			if (!JsonValue.checkType(e)) {
				throw new ClassCastException();
			} else if (e == null) {
				throw new NullPointerException();
			}
			array.add(e);
		}
		bucket.upsert(JsonArrayDocument.create(id, array));
	}

	public CouchbaseQueue(String id, Bucket bucket, Collection<? extends E> content) {
		this.bucket = bucket;
		this.id = id;

		JsonArray array = JsonArray.create();
		for (E e : content) {
			if (!JsonValue.checkType(e)) {
				throw new ClassCastException();
			} else if (e == null) {
				throw new NullPointerException();
			}
			array.add(e);
		}

		bucket.upsert(JsonArrayDocument.create(id, array));
	}

	@Override
	public Iterator<E> iterator() {
		return new JsonArrayDocumentIterator<E>(bucket, id);
	}

	@Override
	public int size() {
		JsonArrayDocument current = bucket.get(id, JsonArrayDocument.class);
		return current.content().size();
	}

	@Override
	public void clear() {
		bucket.upsert(JsonArrayDocument.create(id, JsonArray.empty()));
	}

	@Override
	public boolean offer(E e) {
		if (e == null) {
			throw new NullPointerException("Unsupported null value");
		}
		if (!JsonValue.checkType(e)) {
			throw new IllegalArgumentException("Unsupported value type.");
		}

		bucket.mutateIn(id).arrayPrepend("", e, false).execute();
		return true;
	}

	@Override
	public E poll() {
		String idx = "[-1]"; //FIFO queue as offer uses ARRAY_PREPEND
		for(int i = 0; i < MAX_OPTIMISTIC_LOCKING_ATTEMPTS; i++) {
			try {
				DocumentFragment<Lookup> current = bucket.lookupIn(id).get(idx).execute();
				long returnCas = current.cas();
				Object result = current.content(idx);
				DocumentFragment<Mutation> updated = bucket.mutateIn(id).remove(idx).withCas(returnCas).execute();
				return (E) result;
			} catch (CASMismatchException ex) {
			} catch (MultiMutationException ex) {
				if (ex.firstFailureStatus() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
					return null; //the queue is empty
				}
				throw ex;
			}
		}
		throw new ConcurrentModificationException("Couldn't perform poll in less than " + MAX_OPTIMISTIC_LOCKING_ATTEMPTS + " iterations");
	}

	@Override
	public E peek() {
		try {
			DocumentFragment<Lookup> current = bucket.lookupIn(id).get("[0]").execute();
			Object result = current.content(0);
			return (E) result;
		} catch (MultiMutationException ex) {
			if (ex.firstFailureStatus() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
				return null; //the queue is empty
			}
			throw ex;
		}
	}
}
