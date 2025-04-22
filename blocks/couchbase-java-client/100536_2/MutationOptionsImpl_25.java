package com.couchbase.client.java.internal.options;

import com.couchbase.client.java.ReplicateTo;
import com.couchbase.client.java.options.MutationOptions;
import com.couchbase.client.java.PersistTo;

public class MutationOptionsBuilderImpl implements MutationOptions.Builder {

    private int expiry;
    private PersistTo persistTo = PersistTo.NONE;
    private ReplicateTo replicateTo = ReplicateTo.NONE;

    @Override
    public MutationOptions.Builder expiry(int expiry) {
        this.expiry = expiry;
        return this;
    }

    @Override
    public MutationOptions.Builder replicateTo(ReplicateTo replicateTo) {
        this.replicateTo = replicateTo;
        return this;
    }

    @Override
    public MutationOptions.Builder persistTo(PersistTo persistTo) {
        this.persistTo = persistTo;
        return this;
    }

    @Override
    public MutationOptions build() {
        return new MutationOptionsImpl(0, replicateTo, persistTo);
    }
}
