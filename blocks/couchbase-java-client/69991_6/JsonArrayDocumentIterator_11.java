package com.couchbase.client.java.datastructures.collections.iterators;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonArrayDocument;
import com.couchbase.client.java.error.CASMismatchException;
import com.couchbase.client.java.error.subdoc.MultiMutationException;
import com.couchbase.client.java.subdoc.DocumentFragment;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class JsonArrayDocumentIterator<E> implements Iterator<E> {

	private final Bucket bucket;
	private final String id;

	private long cas;
	private final Iterator<E> delegate;
	private int lastVisited = -1;
	private boolean doneRemove = false;

	public JsonArrayDocumentIterator(Bucket bucket, String id) {
		this.bucket = bucket;
		this.id = id;

		JsonArrayDocument current = bucket.get(id, JsonArrayDocument.class);
		this.cas = current.cas();
		this.delegate = (Iterator<E>) current.content().iterator();
	}

	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public E next() {
		if (hasNext()) {
			lastVisited++;
			doneRemove = false;
		}
		return delegate.next();
	}

	@Override
	public void remove() {
		if (lastVisited < 0) {
			throw new IllegalStateException("Cannot remove before having started iterating");
		}
		if (doneRemove) {
			throw new IllegalStateException("Cannot remove twice in a row while iterating");
		}
		String path = "[" + lastVisited + "]";
		try {
			DocumentFragment<Mutation> itrRemoveResult = bucket.mutateIn(id)
					.withCas(cas)
					.remove(path)
					.execute();
			this.cas = itrRemoveResult.cas();
			delegate.remove();
			doneRemove = true;
			lastVisited--;
		} catch (CASMismatchException e) {
			throw new ConcurrentModificationException("Couldn't remove while iterating: " + e);
		} catch (MultiMutationException e) {
			if (e.firstFailureStatus() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
				throw new IllegalStateException("Invalid remove index " + path);
			}
			throw e;
		}
	}
}
