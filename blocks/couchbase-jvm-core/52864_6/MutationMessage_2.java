
package com.couchbase.client.core.dcp;

public class BucketStreamState {
    public static final BucketStreamState BLANK = new BucketStreamState(0, 0, 0xffffffff, 0, 0);

    private final long vbucketUUID;

    private final long startSequenceNumber;
    private final long endSequenceNumber;
    private final long snapshotStartSequenceNumber;
    private final long snapshotEndSequenceNumber;


    public BucketStreamState(long vbucketUUID,
                             long startSequenceNumber, long endSequenceNumber,
                             long snapshotStartSequenceNumber, long snapshotEndSequenceNumber) {
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
