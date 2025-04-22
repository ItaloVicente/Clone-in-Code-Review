    private TapConnectionProvider conn;
    private TapOpcode opcode;
    private int opaque;
    private OperationCallback cb;

    public TapAck(TapConnectionProvider conn, TapOpcode opcode, int opaque,
        OperationCallback cb) {
