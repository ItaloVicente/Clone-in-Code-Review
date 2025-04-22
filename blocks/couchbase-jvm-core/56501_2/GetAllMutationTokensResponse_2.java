
package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
public class GetAllMutationTokensRequest extends AbstractKeyValueRequest {
    private final PartitionState partitionState;

    public GetAllMutationTokensRequest(final String bucket) {
        this(PartitionState.ANY, bucket);
    }

    public GetAllMutationTokensRequest(final PartitionState partitionState, final String bucket) {
        super("", bucket, null);
        this.partitionState = partitionState;
    }

    public PartitionState partitionState() {
        return partitionState;
    }

    @Override
    public short partition() {
        return DEFAULT_PARTITION;
    }

    public enum PartitionState {
        ANY(0),
        ACTIVE(1),
        REPLICA(2),
        PENDING(3),
        DEAD(4);

        private final int value;

        PartitionState(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }
}
