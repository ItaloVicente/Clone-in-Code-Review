package com.couchbase.client.java;
import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.options.DurabilityOptions;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public abstract class Scope {

    final protected String name;
    final protected Bucket bucket;
    protected DurabilityOptions durabilityOptions;
    protected long timeout;
    protected TimeUnit timeoutUnit;

    protected Scope(final String name, final Bucket bucket) {
        this.name = name;
        this.bucket = bucket;
        this.timeout = bucket.environment().kvTimeout();
        this.timeoutUnit = TimeUnit.MILLISECONDS;
    }

    public Scope durabilityOptions(final DurabilityOptions durabilityOptions) {
        this.durabilityOptions = durabilityOptions;
        return this;
    }

    public Scope kvTimeout(final long timeout, final TimeUnit timeunit) {
        this.timeout = timeout;
        this.timeoutUnit = timeunit;
        return this;
    }

    public DurabilityOptions durabilityOptions() {
        return this.durabilityOptions;
    }

    public long kvTimeout() {
        return this.timeout;
    }

    public abstract Collection collection(String name);

    public abstract Collection collection(String name, long collectionId);

    public abstract ReactiveCollection reactiveCollection(String name);

    public abstract ReactiveCollection reactiveCollection(String name, long collectionId);

    protected abstract void closeCollection(String name);
}
