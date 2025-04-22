
package com.couchbase.client.java.subdoc;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.MutationToken;
import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReplicateTo;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.error.CASMismatchException;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.error.DurabilityException;
import com.couchbase.client.java.error.TranscodingException;
import com.couchbase.client.java.error.subdoc.DocumentNotJsonException;
import com.couchbase.client.java.error.subdoc.MultiMutationException;
import com.couchbase.client.java.util.Blocking;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class MutateInBuilder {

    private final long defaultTimeout;
    private final TimeUnit defaultTimeUnit;
    private final AsyncMutateInBuilder asyncBuilder;

     Instances of this builder should be obtained through {@link Bucket#mutateIn(String)} rather than directly
    @InterfaceAudience.Private
    public MutateInBuilder(AsyncMutateInBuilder asyncBuilder, long defaultTimeout, TimeUnit defaultTimeUnit) {
        this.asyncBuilder = asyncBuilder;
        this.defaultTimeout = defaultTimeout;
        this.defaultTimeUnit = defaultTimeUnit;
    }

    public DocumentFragment<Mutation> doMutate() {
        return doMutate(defaultTimeout, defaultTimeUnit);
    }

    public DocumentFragment<Mutation> doMutate(long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBuilder.doMutate(), timeout, timeUnit);
    }

    public MutateInBuilder withExpiry(int expiry) {
        asyncBuilder.withExpiry(expiry);
        return this;
    }

    public MutateInBuilder withCas(long cas) {
        asyncBuilder.withCas(cas);
        return this;
    }

    public MutateInBuilder withDurability(PersistTo persistTo) {
        asyncBuilder.withDurability(persistTo);
        return this;
    }

    public MutateInBuilder withDurability(ReplicateTo replicateTo) {
        asyncBuilder.withDurability(replicateTo);
        return this;
    }

    public MutateInBuilder withDurability(PersistTo persistTo, ReplicateTo replicateTo) {
        asyncBuilder.withDurability(persistTo, replicateTo);
        return this;
    }

    public <T> MutateInBuilder replace(String path, T fragment) {
        asyncBuilder.replace(path, fragment);
        return this;
    }

    public <T> MutateInBuilder insert(String path, T fragment, boolean createParents) {
        asyncBuilder.insert(path, fragment, createParents);
        return this;
    }

    public <T> MutateInBuilder upsert(String path, T fragment, boolean createParents) {
        asyncBuilder.upsert(path, fragment, createParents);
        return this;
    }


    public <T> MutateInBuilder remove(String path) {
        asyncBuilder.remove(path);
        return this;
    }

    public MutateInBuilder counter(String path, long delta, boolean createParents) {
        asyncBuilder.counter(path, delta, createParents);
        return this;
    }

    public <T> MutateInBuilder extend(String path, T value,
            ExtendDirection direction, boolean createParents) {
        asyncBuilder.extend(path, value, direction, createParents);
        return this;
    }

    public <T> MutateInBuilder arrayInsert(String path, T value) {
        asyncBuilder.arrayInsert(path, value);
        return this;
    }

    public <T> MutateInBuilder addUnique(String path, T value, boolean createParents) {
        asyncBuilder.addUnique(path, value, createParents);
        return this;
    }

    @Override
    public String toString() {
        return asyncBuilder.toString();
    }
}
