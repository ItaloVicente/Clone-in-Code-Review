	public TapOperation tapBackfill(String id, long date, OperationCallback cb) {
		return new TapBackfillOperationImpl(id, date, cb);
	}

	public TapOperation tapCustom(String id, RequestMessage message,
			OperationCallback cb) {
		return new TapCustomOperationImpl(id, message, cb);
	}

	public TapOperation tapAck(TapOpcode opcode, int opaque, OperationCallback cb) {
		return new TapAckOperationImpl(opcode, opaque, cb);
	}
