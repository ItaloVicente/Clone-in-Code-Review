    private final TapConnectionProvider conn;
    private final MemcachedNode node;
    private final TapOpcode opcode;
    private final int opaque;
    private final OperationCallback cb;

    public TapAck(TapConnectionProvider conn, MemcachedNode node,
        TapOpcode opcode, int opaque, OperationCallback cb) {
