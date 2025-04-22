        this(connectionName, ConnectionType.CONSUMER, 0, bucket, password);
    }

    public OpenConnectionRequest(String connectionName, ConnectionType type, String bucket) {
        this(connectionName, type, 0, bucket, null);
    }

    public OpenConnectionRequest(String connectionName, ConnectionType type, String bucket, String password) {
        this(connectionName, type, 0, bucket, password);
    }

    public OpenConnectionRequest(String connectionName, ConnectionType type, int sequenceNumber, String bucket, String password) {
