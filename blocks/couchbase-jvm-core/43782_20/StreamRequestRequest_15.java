
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class SnapshotMarkerMessage extends AbstractDCPRequest {
    public static final int MEMORY = 0x01;
    public static final int DISK = 0x02;
    public static final int CHECKPOINT = 0x04;
    public static final int ACK = 0x08;

    private final long startSequenceNumber;
    private final long endSequenceNumber;

    private final boolean memory;
    private final boolean disk;
    private final boolean checkpoint;
    private final boolean ack;

    public SnapshotMarkerMessage(short partition, long startSequenceNumber, long endSequenceNumber,
                                 int flags, String bucket) {
        this(partition, startSequenceNumber, endSequenceNumber, flags, bucket, null);
    }

    public SnapshotMarkerMessage(short partition, long startSequenceNumber, long endSequenceNumber,
                                 int flags, String bucket, String password) {
        super(bucket, password);
        partition(partition);
        this.startSequenceNumber = startSequenceNumber;
        this.endSequenceNumber = endSequenceNumber;
        this.memory = (flags & MEMORY) == MEMORY;
        this.disk = (flags & DISK) == DISK;
        this.checkpoint = (flags & CHECKPOINT) == CHECKPOINT;
        this.ack = (flags & ACK) == ACK;
    }

    public long startSequenceNumber() {
        return startSequenceNumber;
    }

    public long endSequenceNumber() {
        return endSequenceNumber;
    }

    public boolean memory() {
        return memory;
    }

    public boolean disk() {
        return disk;
    }

    public boolean checkpoint() {
        return checkpoint;
    }

    public boolean ack() {
        return ack;
    }
}
