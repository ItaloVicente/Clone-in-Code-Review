
    static final AttributeKey<Integer> RECEIVED_BYTES = AttributeKey.newInstance("RECEIVED_BYTES");

    public static final ConcurrentMap<String, DCPConnection> connections =
            new ConcurrentHashMap<String, DCPConnection>();

