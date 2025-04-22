
package com.couchbase.client.core.message.dcp;

public class StreamRequestRequest extends AbstractDCPRequest {
    private final long vbucketUUID;

    private final long startSequenceNumber;
    private final long endSequenceNumber;
    private final long snapshotStartSequenceNumber;
    private final long snapshotEndSequenceNumber;

    public StreamRequestRequest(long vbucketUUID, long startSequenceNumber, long endSequenceNumber,
                                long snapshotStartSequenceNumber, long snapshotEndSequenceNumber,
                                String bucket) {
        this(vbucketUUID, startSequenceNumber, endSequenceNumber,
                snapshotStartSequenceNumber, snapshotEndSequenceNumber, bucket, null);
    }

    public StreamRequestRequest(long vbucketUUID, long startSequenceNumber, long endSequenceNumber,
                                long snapshotStartSequenceNumber, long snapshotEndSequenceNumber,
                                String bucket, String password) {
        super(bucket, password);
        this.vbucketUUID = 0;
        this.startSequenceNumber = 0;
        this.endSequenceNumber = 0;
        this.snapshotStartSequenceNumber = 0;
        this.snapshotEndSequenceNumber = 0;
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
