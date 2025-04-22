package com.couchbase.client.java.datastructures;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReplicateTo;


@InterfaceStability.Experimental
@InterfaceAudience.Public
public class MutationOptionBuilder {

    protected int expiry;
    protected long cas;
    protected PersistTo persistTo;
    protected ReplicateTo replicateTo;

    private MutationOptionBuilder() {
        this.expiry = 0;
        this.cas = 0L;
        this.persistTo = PersistTo.NONE;
        this.replicateTo = ReplicateTo.NONE;
    }

    public static MutationOptionBuilder build() {
        return new MutationOptionBuilder();
    }

    public MutationOptionBuilder withExpiry(int expiry) {
        this.expiry = expiry;
        return this;
    }

    public MutationOptionBuilder withCAS(long cas) {
        this.cas = cas;
        return this;
    }

    public MutationOptionBuilder withDurability(PersistTo persistTo) {
        this.persistTo = persistTo;
        return this;
    }

    public MutationOptionBuilder withDurability(ReplicateTo replicateTo) {
        this.replicateTo = replicateTo;
        return this;
    }

    public MutationOptionBuilder withDurability(PersistTo persistTo, ReplicateTo replicateTo) {
        this.persistTo = persistTo;
        this.replicateTo = replicateTo;
        return this;
    }

    public int getExpiry() {
        return this.expiry;
    }

    public long getCAS() {
        return this.cas;
    }

    public PersistTo getPersistTo() {
        return this.persistTo;
    }

    public ReplicateTo getReplicateTo() {
        return this.replicateTo;
    }
}
