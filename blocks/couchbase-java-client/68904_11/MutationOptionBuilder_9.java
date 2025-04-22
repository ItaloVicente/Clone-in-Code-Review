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

    public void withExpiry(int expiry) {
        this.expiry = expiry;
    }

    public void withCAS(long cas) {
        this.cas = cas;
    }

    public void withDurability(PersistTo persistTo) {
        this.persistTo = persistTo;
    }

    public void withDurability(ReplicateTo replicateTo) {
        this.replicateTo = replicateTo;
    }


    public void withDurability(PersistTo persistTo, ReplicateTo replicateTo) {
        this.persistTo = persistTo;
        this.replicateTo = replicateTo;
    }
}
