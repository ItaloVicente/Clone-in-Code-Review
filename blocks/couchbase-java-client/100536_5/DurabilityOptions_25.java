package com.couchbase.client.java.options;

import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReplicateTo;

public class DurabilityOptions {
    private ReplicateTo replicateTo;
    private PersistTo persistTo;

    private DurabilityOptions(ReplicateTo replicateTo, PersistTo persistTo) {
        this.replicateTo = replicateTo;
        this.persistTo = persistTo;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private int expiry;
        private ReplicateTo replicateTo = ReplicateTo.NONE;
        private PersistTo persistTo = PersistTo.NONE;

        public Builder replicateTo(ReplicateTo replicateTo) {
            this.replicateTo = replicateTo;
            return this;
        }

        public Builder persistTo(PersistTo persistTo) {
            this.persistTo = persistTo;
            return this;
        }

        public DurabilityOptions build() {
            return new DurabilityOptions(replicateTo, persistTo);
        }
    }

    public ReplicateTo replicateTo() { return this.replicateTo; }

    public PersistTo persistTo() { return this.persistTo; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DurabilityOptions{");
        sb.append("replicateTo:" + this.replicateTo().toString() + ",");
        sb.append("persistTo:" + this.persistTo().toString() + "}");
        return sb.toString();
    }
}
