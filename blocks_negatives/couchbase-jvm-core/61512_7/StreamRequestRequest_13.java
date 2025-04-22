    public StreamRequestRequest(short partition, String bucket) {
        this(partition, 0, 0, 0xffffffff, 0, 0, bucket, null);
    }

    public StreamRequestRequest(short partition, long vbucketUUID, long startSequenceNumber, long endSequenceNumber,
                                long snapshotStartSequenceNumber, long snapshotEndSequenceNumber,
                                String bucket) {
        this(partition, vbucketUUID, startSequenceNumber, endSequenceNumber,
                snapshotStartSequenceNumber, snapshotEndSequenceNumber, bucket, null);
    }

