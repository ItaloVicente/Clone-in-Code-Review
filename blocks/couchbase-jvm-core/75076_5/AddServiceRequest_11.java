        super(bucket, bucket, password);
        this.type = type;
        this.hostname = hostname;
        this.port = port;
    }

    public AddServiceRequest(ServiceType type, String bucket, String username, String password, int port, InetAddress hostname) {
        super(bucket, username, password);
