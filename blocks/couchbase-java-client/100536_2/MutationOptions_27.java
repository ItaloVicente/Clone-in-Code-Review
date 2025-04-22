package com.couchbase.client.java.options;

import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReplicateTo;

public abstract class MutationOptions {

    protected MutationOptions() {}

    public interface Builder {

        Builder expiry(int expiry);

        Builder replicateTo(ReplicateTo replicaMode);

        Builder persistTo(PersistTo persistTo);

        MutationOptions build();
    }

    public abstract int expiry();

    public abstract ReplicateTo replicateTo();

    public abstract PersistTo persistTo();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MutationOptions{");
        sb.append("expiry:" + this.expiry() + ",");
        sb.append("replicateTo:" + this.replicateTo().toString() + ",");
        sb.append("persistTo:" + this.persistTo().toString() + "}");
        return sb.toString();
    }
}
