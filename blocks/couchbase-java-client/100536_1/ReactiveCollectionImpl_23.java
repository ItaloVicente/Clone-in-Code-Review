package com.couchbase.client.java.internal;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.ClusterFacade;
import com.couchbase.client.core.message.observe.Observe;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReactiveCollection;
import com.couchbase.client.java.ReplicaMode;
import com.couchbase.client.java.ReplicateTo;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.bucket.ReplicaReader;
import com.couchbase.client.java.bucket.api.Exists;
import com.couchbase.client.java.bucket.api.Get;
import com.couchbase.client.java.bucket.api.Mutate;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonLongDocument;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.error.DurabilityException;
import com.couchbase.client.java.options.MutationOptions;
import com.couchbase.client.java.transcoder.Transcoder;
import io.opentracing.Span;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;

public class ReactiveCollectionImpl extends ReactiveCollection {

	private final String bucketName;
	private final CouchbaseEnvironment env;
	private final Map<Class<? extends Document>, Transcoder<? extends Document, ?>> transcoders;
	private final ClusterFacade core;

	protected ReactiveCollectionImpl(final String name, final long cid, final Scope scope, final Bucket bucket) {
		super(name, cid, scope, bucket);
		this.bucketName = bucket.name();
		this.env = bucket.environment();
		this.transcoders = bucket.async().transcoders();
		this.core = bucket.core();
	}

	@Override
	public <D extends Document<?>> Observable<D> get(String id, Class<D> target, long timeout, TimeUnit timeunit) {
		return Get.get(this.collectionId, id, target, env, bucketName, core, transcoders, timeout, timeunit);
	}

	@Override
	public <D extends Document<?>> Observable<D> getFromReplica(String id, ReplicaMode mode, Class<D> target, long timeout, TimeUnit timeunit) {
		return ReplicaReader.read(this.collectionId, core, id, mode, bucketName, transcoders, target, env, timeout, timeunit);
	}

	@Override
	public <D extends Document<?>> Observable<D> getAndLock(String id, int lockTime, Class<D> target, long timeout, TimeUnit timeunit) {
		return Get.getAndLock(this.collectionId, id, target, env, bucketName, core, transcoders, lockTime, timeout, timeunit);
	}

	@Override
	public Observable<Boolean> exists(String id, long timeout, TimeUnit timeunit) {
		return Exists.exists(this.collectionId, id, env, core, bucketName, timeout, timeunit);
	}

	@Override
	public <D extends Document<?>> Observable<D> getAndTouch(String id, int expiry, Class<D> target, long timeout, TimeUnit timeunit) {
		return Get.getAndTouch(this.collectionId, id, target, env, bucketName, core, transcoders, expiry, timeout, timeunit);
	}

	@Override
	public Observable<Boolean> touch(String id, int expiry, long timeout, TimeUnit timeunit) {
		return null;
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public <D extends Document<?>> Observable<D> insert(final D document, final MutationOptions options, final long timeout, final TimeUnit timeunit) {
		final Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());

		if (options == null || options.persistTo() == PersistTo.NONE && options.replicateTo() == ReplicateTo.NONE) {
			return Mutate.insert(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, null);
		} else {
			final Span parent = startTracing("insert_with_durability");
			return addTracing(parent, options, timeout, timeunit, Mutate.insert(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, parent));
		}
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public <D extends Document<?>> Observable<D> upsert(final D document, final MutationOptions options, final long timeout, final TimeUnit timeunit) {
		final Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());

		if (options == null || options.persistTo() == PersistTo.NONE && options.replicateTo() == ReplicateTo.NONE) {
			return Mutate.upsert(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, null);
		} else {
			final Span parent = startTracing("upsert_with_durability");
			return addTracing(parent, options, timeout, timeunit, Mutate.upsert(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, parent));
		}
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public <D extends Document<?>> Observable<D> append(D document, MutationOptions options, long timeout, TimeUnit timeunit) {
		final Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());

		if (options == null || options.persistTo() == PersistTo.NONE && options.replicateTo() == ReplicateTo.NONE) {
			return Mutate.append(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, null);
		} else {
			final Span parent = startTracing("append_with_durability");
			return addTracing(parent, options, timeout, timeunit, Mutate.append(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, null));
		}
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public <D extends Document<?>> Observable<D> prepend(D document, MutationOptions options, long timeout, TimeUnit timeunit) {
		final Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());

		if (options == null || options.persistTo() == PersistTo.NONE && options.replicateTo() == ReplicateTo.NONE) {
			return Mutate.prepend(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, null);
		} else {
			final Span parent = startTracing("prepend_with_durability");
			return addTracing(parent, options, timeout, timeunit, Mutate.prepend(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, null));
		}
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public <D extends Document<?>> Observable<D> replace(D document, MutationOptions options, long timeout, TimeUnit timeunit) {
		final Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());

		if (options == null || options.persistTo() == PersistTo.NONE && options.replicateTo() == ReplicateTo.NONE) {
			return Mutate.replace(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, null);
		} else {
			final Span parent = startTracing("replace_with_durability");
			return addTracing(parent, options, timeout, timeunit, Mutate.replace(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, parent));
		}
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public Observable<JsonLongDocument> counter(String id, long delta, long initial, MutationOptions options, long timeout, TimeUnit timeunit) {
		if (options == null || options.persistTo() == PersistTo.NONE && options.replicateTo() == ReplicateTo.NONE) {
			return Mutate.counter(this.collectionId, id, delta, 0, options.expiry(), env, core, bucketName, timeout, timeunit, null);
		} else {
			final Span parent = startTracing("counter_with_durability");
			return addTracing(parent, options, timeout, timeunit, Mutate.counter(this.collectionId, id, delta, 0, options.expiry(), env, core, bucketName, timeout, timeunit, parent));
		}
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public Observable<Boolean> unlock(String id, long cas, long timeout, TimeUnit timeunit) {
		return Mutate.unlock(this.collectionId, id, cas, env, core, bucketName, timeout, timeunit);
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public <D extends Document<?>> Observable<D> remove(String id, Class<D> target, MutationOptions options, long timeout, TimeUnit timeunit) {
		final  Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(target);
		return this.remove((D) transcoder.newDocument(id, 0, null, 0, null), options, timeout, timeunit);
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public <D extends Document<?>> Observable<D> remove(D document, MutationOptions options, long timeout, TimeUnit timeunit) {
		final Transcoder<Document<Object>, Object> transcoder = (Transcoder<Document<Object>, Object>) transcoders.get(document.getClass());

		if (options == null || options.persistTo() == PersistTo.NONE && options.replicateTo() == ReplicateTo.NONE) {
			return Mutate.remove(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, null);
		} else {
			final Span parent = startTracing("remove_with_durability");
			return addTracing(parent, options, timeout, timeunit, Mutate.remove(this.collectionId, document, env, transcoder, core, bucketName, timeout, timeunit, parent));
		}
	}

	private Span startTracing(String spanName) {
		if (!env.operationTracingEnabled()) {
			return null;
		}
		io.opentracing.Scope scope = env.tracer()
				.buildSpan(spanName)
				.startActive(false);
		Span parent = scope.span();
		scope.close();
		return parent;
	}

	private Action0 stopTracing(final Span parent) {
		return new Action0() {
			@Override
			public void call() {
				if (parent != null) {
					env.tracer().scopeManager()
							.activate(parent, true)
							.close();
				}
			}
		};
	}

	private <D extends Document<?>> Observable<D> addTracing(final Span parent, final MutationOptions options, final long timeout, final TimeUnit timeunit, final Observable<D> obs) {
		return obs.flatMap(new Func1<D, Observable<D>>() {
			@Override
			public Observable<D> call(final D doc) {
				Observable<D> or = Observe
						.call(core, bucketName, doc.id(), doc.cas(), false, doc.mutationToken(),
								options.persistTo().value(), options.replicateTo().value(),
								env.observeIntervalDelay(), env.retryStrategy(), parent)
						.map(new Func1<Boolean, D>() {
							@Override
							public D call(Boolean aBoolean) {
								return doc;
							}
						})
						.onErrorResumeNext(new Func1<Throwable, Observable<? extends D>>() {
							@Override
							public Observable<? extends D> call(Throwable throwable) {
								return Observable.error(new DurabilityException(
										"Durability requirement failed: " + throwable.getMessage(),
										throwable));
							}
						});
				return timeout > 0 ? or.timeout(timeout, timeunit, env.scheduler()) : or;
			}
		}).doOnTerminate(stopTracing(parent));
	}
}
