class TapAck {
	private TapConnectionProvider conn;
	private TapOpcode opcode;
	private int opaque;
	private OperationCallback cb;

	public TapAck(TapConnectionProvider conn, TapOpcode opcode, int opaque, OperationCallback cb) {
		this.conn = conn;
		this.opcode = opcode;
		this.opaque = opaque;
		this.cb = cb;
	}

	public TapConnectionProvider getConn() {
		return conn;
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
