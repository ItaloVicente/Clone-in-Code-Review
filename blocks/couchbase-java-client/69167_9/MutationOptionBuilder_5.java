package com.couchbase.client.java.datastructures;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReplicateTo;


@InterfaceStability.Experimental
@InterfaceAudience.Public
public class MutationOptionBuilder {

    private int expiry;
    private long cas;
    private PersistTo persistTo;
    private ReplicateTo replicateTo;

    private MutationOptionBuilder() {
        this.expiry = 0;
        this.cas = 0L;
        this.persistTo = PersistTo.NONE;
        this.replicateTo = ReplicateTo.NONE;
    }

    public static MutationOptionBuilder builder() {
        return new MutationOptionBuilder();
    }

    public MutationOptionBuilder expiry(int expiry) {
        this.expiry = expiry;
        return this;
    }


    public int expiry() {
        return this.expiry;
    }

    public MutationOptionBuilder cas(long cas) {
        this.cas = cas;
        return this;
    }

    public long cas() {
        return cas;
    }

    public MutationOptionBuilder persistTo(PersistTo persistTo) {
        this.persistTo = persistTo;
        return this;
    }

    public PersistTo persistTo() {
        return this.persistTo;
    }

    public MutationOptionBuilder replicateTo(ReplicateTo replicateTo) {
        this.replicateTo = replicateTo;
        return this;
    }


    public ReplicateTo replicateTo() {
        return this.replicateTo;
    }
}
