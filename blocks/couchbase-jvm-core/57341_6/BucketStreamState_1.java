    public BucketStreamState(short partition, long vbucketUUID, long sequenceNumber) {
        this.partition = partition;
        this.vbucketUUID = vbucketUUID;
        this.startSequenceNumber = sequenceNumber;
        this.endSequenceNumber = sequenceNumber;
        this.snapshotStartSequenceNumber = sequenceNumber;
        this.snapshotEndSequenceNumber = sequenceNumber;
    }

