    public StreamEndMessage(short partition, final Reason reason, String bucket) {
        this(partition, reason, bucket, null);
    }

    public StreamEndMessage(short partition, final Reason reason, final String bucket, final String password) {
        super(partition, null, bucket, password);
