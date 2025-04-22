package com.couchbase.client.java.datastructures.collections;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.java.Bucket;
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
public class CouchbaseArrayList<E> extends AbstractList<E> {

	private static final int MAX_OPTIMISTIC_LOCKING_ATTEMPTS = Integer.parseInt(System.getProperty("com.couchbase.datastructureCASRetryLimit", "10"));
	private final String id;
	private final Bucket bucket;

	public CouchbaseArrayList(String id, Bucket bucket) {
		this.bucket = bucket;
		this.id = id;

		try {
			bucket.insert(JsonArrayDocument.create(id, JsonArray.empty()));
		} catch (DocumentAlreadyExistsException ex) {
		}
	}

	public CouchbaseArrayList(String id, Bucket bucket, E... content) {
		this.bucket = bucket;
		this.id = id;

		bucket.upsert(JsonArrayDocument.create(id, JsonArray.from(content)));
	}

	public CouchbaseArrayList(String id, Bucket bucket, Collection<? extends E> content) {
		this.bucket = bucket;
		this.id = id;

		JsonArray array;
		if (content instanceof List) {
			array = JsonArray.from((List) content);
		} else {
			array = JsonArray.create();
			for (E e : content) {
				array.add(e);
			}
		}

		bucket.upsert(JsonArrayDocument.create(id, array));
	}

	@Override
	public E get(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
		String idx = "[" + index + "]";

		DocumentFragment<Lookup> result = bucket.lookupIn(id).get(idx).execute();
		if (result.status(idx) == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}

		return (E) result.content(idx);
	}

	@Override
	public int size() {
		JsonArrayDocument current = bucket.get(id, JsonArrayDocument.class);
		return current.content().size();
	}

	@Override
	public boolean isEmpty() {
		DocumentFragment<Lookup> current = bucket.lookupIn(id).exists("[0]").execute();
		return current.status("[0]") == ResponseStatus.SUBDOC_PATH_NOT_FOUND;
	}

	@Override
	public E set(int index, E element) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
		if (!JsonValue.checkType(element)) {
			throw new IllegalArgumentException("Unsupported value type.");
		}
		String idx = "["+index+"]";

		for(int i = 0; i < MAX_OPTIMISTIC_LOCKING_ATTEMPTS; i++) {
			try {
				DocumentFragment<Lookup> current = bucket.lookupIn(id).get(idx).execute();
				long returnCas = current.cas();
				Object result = current.content(idx);
				bucket.mutateIn(id).replace(idx, element).withCas(returnCas).execute();
				return (E) result;
			} catch (CASMismatchException ex) {
			} catch (MultiMutationException ex) {
				if (ex.firstFailureStatus() == ResponseStatus.SUBDOC_PATH_NOT_FOUND
						|| ex.firstFailureStatus() == ResponseStatus.SUBDOC_PATH_INVALID) {
					throw new IndexOutOfBoundsException("Index: " + index);
				}
				throw ex;
			}
		}
		throw new ConcurrentModificationException("Couldn't perform set in less than " + MAX_OPTIMISTIC_LOCKING_ATTEMPTS + " iterations");
	}

	@Override
	public void add(int index, E element) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
		if (!JsonValue.checkType(element)) {
			throw new IllegalArgumentException("Unsupported value type.");
		}

		try {
			bucket.mutateIn(id).arrayInsert("["+index+"]", element).execute();
		} catch (MultiMutationException ex) {
			if (ex.firstFailureStatus() == ResponseStatus.SUBDOC_PATH_NOT_FOUND ||
					ex.firstFailureStatus() == ResponseStatus.SUBDOC_PATH_INVALID) {
				throw new IndexOutOfBoundsException("Index: " + index);
			}
			throw ex;
		}
	}

	@Override
	public E remove(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
		String idx = "[" + index + "]";
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
					throw new IndexOutOfBoundsException("Index: " + index);
				}
				throw ex;
			}
		}
		throw new ConcurrentModificationException("Couldn't perform remove in less than " + MAX_OPTIMISTIC_LOCKING_ATTEMPTS + " iterations");
	}

	@Override
	public boolean contains(Object o) {
		return super.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return new CouchbaseListIterator(0);
	}

	@Override
	public ListIterator<E> listIterator() {
		return new CouchbaseListIterator(0);
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new CouchbaseListIterator(index);
	}

	@Override
	public void clear() {
		bucket.upsert(JsonArrayDocument.create(id, JsonArray.empty()));
	}

	private class CouchbaseListIterator implements ListIterator<E> {

		private long cas;
		private final ListIterator<E> delegate;

		private int cursor;
		private int lastVisited;

		public CouchbaseListIterator(int index) {
			JsonArrayDocument current = bucket.get(id, JsonArrayDocument.class);
			List<E> list = new ArrayList<E>(current.content().size());
			for (E value : (Iterable<E>) current.content()) {
				list.add(value);
			}
			this.cas = current.cas();
			this.delegate = list.listIterator(index);
			this.lastVisited = -1;
			this.cursor = index;
		}

		@Override
		public boolean hasNext() {
			return delegate.hasNext();
		}

		@Override
		public E next() {
			E next = delegate.next();
			lastVisited = cursor;
			cursor++;
			return next;
		}

		@Override
		public boolean hasPrevious() {
			return delegate.hasPrevious();
		}

		@Override
		public E previous() {
			E previous = delegate.previous();
			cursor--;
			lastVisited = cursor;
			return previous;
		}

		@Override
		public int nextIndex() {
			return delegate.nextIndex();
		}

		@Override
		public int previousIndex() {
			return delegate.previousIndex();
		}

		@Override
		public void remove() {
			if (lastVisited < 0) {
				throw new IllegalStateException();
			}
			int index = lastVisited;
			String idx = "[" + index + "]";
			try {
				DocumentFragment<Mutation> updated = bucket.mutateIn(id).remove(idx).withCas(this.cas).execute();
				this.cas = updated.cas();
				delegate.remove();
				this.cursor = lastVisited;
				this.lastVisited = -1;
			} catch (CASMismatchException ex) {
				throw new ConcurrentModificationException("List was modified since iterator creation: " + ex);
			} catch (MultiMutationException ex) {
				if (ex.firstFailureStatus() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
					throw new ConcurrentModificationException("Element doesn't exist anymore at index: " + index);
				}
				throw ex;
			}
		}

		@Override
		public void set(E e) {
			if (lastVisited < 0) {
				throw new IllegalStateException();
			}
			int index = lastVisited;
			String idx = "[" + index + "]";
			try {
				DocumentFragment<Mutation> updated = bucket.mutateIn(id).replace(idx, e).withCas(this.cas).execute();
				this.cas = updated.cas();
				delegate.set(e);
			} catch (CASMismatchException ex) {
				throw new ConcurrentModificationException("List was modified since iterator creation: " + ex);
			} catch (MultiMutationException ex) {
				if (ex.firstFailureStatus() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
					throw new ConcurrentModificationException("Element doesn't exist anymore at index: " + index);
				}
				throw ex;
			}
		}

		@Override
		public void add(E e) {
			int index = this.cursor;
			String idx = "[" + index + "]";
			try {
				DocumentFragment<Mutation> updated = bucket.mutateIn(id).arrayInsert(idx, e).withCas(this.cas).execute();
				this.cas = updated.cas();
				delegate.add(e);
				this.cursor++;
				this.lastVisited = -1;
			} catch (CASMismatchException ex) {
				throw new ConcurrentModificationException("List was modified since iterator creation: " + ex);
			} catch (MultiMutationException ex) {
				if (ex.firstFailureStatus() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
					throw new ConcurrentModificationException("Element doesn't exist anymore at index: " + index);
				}
				throw ex;
			}
		}
	}
}
