        this(totalBodyLength, partition, key, content, expiration, bySequenceNumber, revisionSequenceNumber,
                flags, lockTime, cas, bucket, bucket, password);
    }

    public MutationMessage(int totalBodyLength, short partition, String key, ByteBuf content, int expiration,
                           long bySequenceNumber, long revisionSequenceNumber,
                           int flags, int lockTime, long cas, String bucket, String username, String password) {
        super(totalBodyLength, partition, key, bucket, username, password);
