
  class TapAck {
    private final TapConnectionProvider conn;
    private final MemcachedNode node;
    private final TapOpcode opcode;
    private final int opaque;
    private final OperationCallback cb;

    public TapAck(TapConnectionProvider conn, MemcachedNode node,
        TapOpcode opcode, int opaque, OperationCallback cb) {
      this.conn = conn;
      this.node = node;
      this.opcode = opcode;
      this.opaque = opaque;
      this.cb = cb;
    }

    public TapConnectionProvider getConn() {
      return conn;
    }

    public MemcachedNode getNode() {
      return node;
    }

    public TapOpcode getOpcode() {
      return opcode;
    }

    public int getOpaque() {
      return opaque;
    }

    public OperationCallback getCallback() {
      return cb;
    }
  }
