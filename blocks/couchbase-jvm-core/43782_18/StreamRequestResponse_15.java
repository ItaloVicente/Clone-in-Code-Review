
package com.couchbase.client.core.message.dcp;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class StreamRequestRequest extends AbstractDCPRequest {
    private final long vbucketUUID;

    private final long startSequenceNumber;
    private final long endSequenceNumber;
    private final long snapshotStartSequenceNumber;
    private final long snapshotEndSequenceNumber;


    public StreamRequestRequest(short partition, String bucket) {
        this(partition, 0, 0, 0xffffffff, 0, 0, bucket, null);
    }

    public StreamRequestRequest(short partition, long vbucketUUID, long startSequenceNumber, long endSequenceNumber,
                                long snapshotStartSequenceNumber, long snapshotEndSequenceNumber,
                                String bucket) {
        this(partition, vbucketUUID, startSequenceNumber, endSequenceNumber,
                snapshotStartSequenceNumber, snapshotEndSequenceNumber, bucket, null);
    }

    public StreamRequestRequest(short partition, long vbucketUUID, long startSequenceNumber, long endSequenceNumber,
                                long snapshotStartSequenceNumber, long snapshotEndSequenceNumber,
                                String bucket, String password) {
        super(bucket, password);
        this.partition(partition);
        this.vbucketUUID = vbucketUUID;
        this.startSequenceNumber = startSequenceNumber;
        this.endSequenceNumber = endSequenceNumber;
        this.snapshotStartSequenceNumber = snapshotStartSequenceNumber;
        this.snapshotEndSequenceNumber = snapshotEndSequenceNumber;
    }

    public long vbucketUUID() {
        return vbucketUUID;
    }

    public long startSequenceNumber() {
        return startSequenceNumber;
    }

    public long endSequenceNumber() {
        return endSequenceNumber;
    }

    public long snapshotStartSequenceNumber() {
        return snapshotStartSequenceNumber;
    }

    public long snapshotEndSequenceNumber() {
        return snapshotEndSequenceNumber;
    }
}
