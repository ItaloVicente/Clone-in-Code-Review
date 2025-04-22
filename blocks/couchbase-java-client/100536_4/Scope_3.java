package com.couchbase.client.java;
import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.options.MutationOptions;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public abstract class Scope {

    final protected String name;
    final protected Bucket bucket;
    protected MutationOptions mutationOptions;
    protected long timeout;
    protected TimeUnit timeoutUnit;

    protected Scope(final String name, final Bucket bucket) {
        this.name = name;
        this.bucket = bucket;
        this.timeout = bucket.environment().kvTimeout();
        this.timeoutUnit = TimeUnit.MILLISECONDS;
    }

    public Scope withOperationTimeout(final long timeout, final TimeUnit timeunit) {
        this.timeout = timeout;
        this.timeoutUnit = timeunit;
        return this;
    }

    public Scope withMutationOptions(final MutationOptions mutationOptions) {
        this.mutationOptions = mutationOptions;
        return this;
    }

    public abstract Collection collection(String name);

    public abstract Collection collection(String name, long cid);

    public abstract ReactiveCollection reactiveCollection(String name);

    public abstract ReactiveCollection reactiveCollection(String name, long cid);

    public abstract void closeCollection(String name);

    public abstract void dropCollection(String name);

}
