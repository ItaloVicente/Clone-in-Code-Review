package com.couchbase.client.java;

import java.util.List;
import java.util.concurrent.TimeUnit;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonLongDocument;
import com.couchbase.client.java.options.MutationOptions;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public abstract class Collection {

	final protected String name;
	final protected long collectionId;
	final protected Scope scope;
	final protected Bucket asyncBucket;
	protected long timeout;
	protected TimeUnit timeoutUnit;
	protected MutationOptions mutationOptions;
	protected ReactiveCollection reactiveCollection;

	protected Collection(final String name, final long collectionId, final Scope scope, final Bucket bucket) {
		this.name = name;
		this.collectionId = collectionId;
		this.scope = scope;
		this.asyncBucket = bucket;
	}

	public Collection withMutationOptions(MutationOptions options) {
		this.mutationOptions = options;
		return this;
	}

	public Collection withOperationTimeout(long timeout, TimeUnit timeunit) {
		this.timeout = timeout;
		this.timeoutUnit = timeunit;
		return this;
	}

	public abstract ReactiveCollection reactiveCollection();

	public <D extends Document<?>> D get(String id, Class<D> target) {
		return this.get(id, target, this.timeout, this.timeoutUnit);
	}

	public abstract <D extends Document<?>> D get(String id, Class<D> target, long timeout, TimeUnit timeunit);

	public <D extends Document<?>> List<D> getFromReplica(String id, ReplicaMode mode, Class<D> target) {
		return this.getFromReplica(id, mode, target, this.timeout, this.timeoutUnit);
	}

	public abstract <D extends Document<?>> List<D> getFromReplica(String id, ReplicaMode mode, Class<D> target, long timeout, TimeUnit timeunit);

	public <D extends Document<?>> D getAndLock(String id, int lockTime, Class<D> target) {
		return this.getAndLock(id, lockTime, target, this.timeout, this.timeoutUnit);
	}

	public abstract <D extends Document<?>> D getAndLock(String id, int lockTime, Class<D> target, long timeout, TimeUnit timeunit);

	public boolean exists(String id) {
		return this.exists(id, this.timeout, this.timeoutUnit);
	}

	public abstract boolean exists(String id, long timeout, TimeUnit timeunit);

	public <D extends Document<?>> D getAndTouch(String id, int expiry, Class<D> target) {
		return this.getAndTouch(id, expiry, target, this.timeout, this.timeoutUnit);
	}

	public abstract <D extends Document<?>> D getAndTouch(String id, int expiry, Class<D> target, long timeout, TimeUnit timeunit);

	public boolean touch(String id, int expiry) {
		return this.touch(id, expiry, this.timeout, this.timeoutUnit);
	}

	public abstract boolean touch(String id, int expiry, long timeout, TimeUnit timeunit);

	public <D extends Document<?>> D insert(D document) {
		return this.insert(document, this.mutationOptions, this.timeout, this.timeoutUnit);
	}

	public <D extends Document<?>> D insert(D document, long timeout, TimeUnit timeunit) {
		return this.insert(document, this.mutationOptions, timeout, timeunit);
	}

	public <D extends Document<?>> D insert(D document, MutationOptions options) {
		return this.insert(document, options, this.timeout, this.timeoutUnit);
	}

	public abstract <D extends Document<?>> D insert(D document, MutationOptions options, long timeout, TimeUnit timeunit);

	public <D extends Document<?>> D upsert(D document) {
		return this.upsert(document, this.mutationOptions, this.timeout, this.timeoutUnit);
	}

	public <D extends Document<?>> D upsert(D document, long timeout, TimeUnit timeunit) {
		return this.upsert(document, this.mutationOptions, timeout, timeunit);
	}

	public <D extends Document<?>> D upsert(D document, MutationOptions options) {
		return this.upsert(document, options, this.timeout, this.timeoutUnit);
	}

	public abstract <D extends Document<?>> D upsert(D document, MutationOptions options, long timeout, TimeUnit timeunit);

	public <D extends Document<?>> D append(D document) {
		return this.append(document, this.mutationOptions, this.timeout, this.timeoutUnit);
	}

	public <D extends Document<?>> D append(D document, long timeout, TimeUnit timeunit) {
		return this.append(document, this.mutationOptions, timeout, timeunit);
	}

	public <D extends Document<?>> D append(D document, MutationOptions options) {
		return this.append(document, options, this.timeout, this.timeoutUnit);
	}

	public abstract <D extends Document<?>> D append(D document, MutationOptions options, long timeout, TimeUnit timeunit);

	public <D extends Document<?>> D prepend(D document) {
		return this.prepend(document, this.mutationOptions, this.timeout, this.timeoutUnit);
	}

	public <D extends Document<?>> D prepend(D document, long timeout, TimeUnit timeunit) {
		return this.prepend(document, this.mutationOptions, timeout, timeunit);
	}

	public <D extends Document<?>> D prepend(D document, MutationOptions options) {
		return this.prepend(document, options, this.timeout, this.timeoutUnit);
	}

	public abstract <D extends Document<?>> D prepend(D document, MutationOptions options, long timeout, TimeUnit timeunit);

	public <D extends Document<?>> D replace(D document) {
		return this.replace(document, this.mutationOptions, this.timeout, this.timeoutUnit);
	}

	public <D extends Document<?>> D replace(D document, long timeout, TimeUnit timeunit) {
		return this.replace(document, this.mutationOptions, timeout, timeunit);
	}

	public <D extends Document<?>> D replace(D document, MutationOptions options) {
		return this.replace(document, options, this.timeout, this.timeoutUnit);
	}

	public abstract <D extends Document<?>> D replace(D document, MutationOptions options, long timeout, TimeUnit timeunit);

	public JsonLongDocument counter(String id, long delta) {
		return this.counter(id, delta, 0, this.mutationOptions, this.timeout, this.timeoutUnit);
	}

	public JsonLongDocument counter(String id, long delta, long timeout, TimeUnit timeunit) {
		return this.counter(id, delta, 0, this.mutationOptions, timeout, timeunit);
	}

	public JsonLongDocument counter(String id, long delta, MutationOptions options) {
		return this.counter(id, delta, 0, options, this.timeout, this.timeoutUnit);
	}

	public JsonLongDocument counter(String id, long delta, MutationOptions options, long timeout, TimeUnit timeunit) {
		return this.counter(id, delta, 0, options, timeout, timeunit);
	}

	public JsonLongDocument counter(String id, long delta, long initial) {
		return this.counter(id, delta, initial, this.mutationOptions, this.timeout, this.timeoutUnit);
	}

	public JsonLongDocument counter(String id, long delta, long initial, long timeout, TimeUnit timeunit) {
		return this.counter(id, delta, initial, this.mutationOptions, timeout, timeunit);
	}

	public JsonLongDocument counter(String id, long delta, long initial, MutationOptions options) {
		return this.counter(id, delta, initial, options, this.timeout, this.timeoutUnit);
	}

	public abstract JsonLongDocument counter(String id, long delta, long initial, MutationOptions options, long timeout, TimeUnit timeunit);


	public boolean unlock(String id) {
		return this.unlock(id, 0, this.timeout, this.timeoutUnit);
	}

	public boolean unlock(String id, long timeout, TimeUnit timeunit) {
		return this.unlock(id, 0, timeout, timeunit);
	}

	public boolean unlock(String id, long cas) {
		return this.unlock(id, cas, this.timeout, this.timeoutUnit);
	}

	public abstract boolean unlock(String id, long cas, long timeout, TimeUnit timeunit);

	public <D extends Document<?>> D remove(String id, Class<D> target) {
		return this.remove(id, target, this.mutationOptions, this.timeout, this.timeoutUnit);
	}

	public <D extends Document<?>> D remove(String id, Class<D> target, long timeout, TimeUnit timeunit) {
		return this.remove(id, target, this.mutationOptions, timeout, timeunit);
	}

	public <D extends Document<?>> D remove(String id, Class<D> target, MutationOptions options) {
		return this.remove(id, target, options, this.timeout, this.timeoutUnit);
	}

	public abstract <D extends Document<?>> D remove(String id, Class<D> target, MutationOptions options, long timeout, TimeUnit timeunit);

	public <D extends Document<?>> D remove(D document) {
		return this.remove(document, this.mutationOptions, this.timeout, this.timeoutUnit);
	}

	public <D extends Document<?>> D remove(D document, long timeout, TimeUnit timeunit) {
		return this.remove(document, this.mutationOptions, timeout, timeunit);
	}

	public <D extends Document<?>> D remove(D document, MutationOptions options) {
		return this.remove(document, options, this.timeout, this.timeoutUnit);
	}

	public abstract <D extends Document<?>> D remove(D document, MutationOptions options, long timeout, TimeUnit timeunit);

	public String name() {
		return this.name;
	}

	public long collectionId() {
		return this.collectionId;
	}
}
