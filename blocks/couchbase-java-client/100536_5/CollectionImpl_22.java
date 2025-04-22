package com.couchbase.client.java.internal;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.utils.Blocking;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.ReactiveCollection;
import com.couchbase.client.java.ReplicaMode;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonLongDocument;
import com.couchbase.client.java.options.DurabilityOptions;

public class CollectionImpl extends Collection {

    protected CollectionImpl(final String name, final long cid, final Scope scope, final Bucket bucket) {
        super(name, cid, scope, bucket);
        this.reactiveCollection = new ReactiveCollectionImpl(name, cid, scope, bucket);
        this.reactiveCollection.withOperationTimeout(this.timeout, this.timeoutUnit);
        this.reactiveCollection.withDurabilityOptions(this.durabilityOptions);
    }

    public ReactiveCollection reactiveCollection() {
        return this.reactiveCollection;
    }

    @Override
    public <D extends Document<?>> D get(String id, Class<D> target, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.get(id, target, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public <D extends Document<?>> D getAndLock(String id, int lockTime, Class<D> target, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.getAndLock(id, lockTime, target, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public <D extends Document<?>> List<D> getFromReplica(String id, ReplicaMode mode, Class<D> target, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.getFromReplica(id, mode, target, timeout, timeunit).toList(), timeout, timeunit);
    }

    @Override
    public boolean exists(String id, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.exists(id, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public <D extends Document<?>> D getAndTouch(String id, int expiry, Class<D> target, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.getAndTouch(id, expiry, target, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public boolean touch(String id, int expiry, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.touch(id, expiry, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public <D extends Document<?>> D insert(D document, DurabilityOptions options, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.insert(document, options, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public <D extends Document<?>> D upsert(D document, DurabilityOptions options, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.upsert(document, options, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public <D extends Document<?>> D append(D document, DurabilityOptions options, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.append(document, options, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public <D extends Document<?>> D prepend(D document, DurabilityOptions options, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.prepend(document, options, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public <D extends Document<?>> D replace(D document, DurabilityOptions options, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.replace(document, options, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public JsonLongDocument counter(String id, long delta, long initial, DurabilityOptions options, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.counter(id, delta, initial, options, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public boolean unlock(String id, long cas, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.unlock(id, cas, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public <D extends Document<?>> D remove(String id, Class<D> target, DurabilityOptions options, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.remove(id, target, timeout, timeunit), timeout, timeunit);
    }

    @Override
    public <D extends Document<?>> D remove(D document, DurabilityOptions options, long timeout, TimeUnit timeunit) {
        return Blocking.blockForSingle(this.reactiveCollection.remove(document, timeout, timeunit), timeout, timeunit);
    }
}
