	public TapOperation tapBackfill(String id, Date date, String keyFilter,
			String valueFilter, OperationCallback cb) {
		return new TapBackfillOperationImpl(id, date, keyFilter, valueFilter, cb);
	}

	public TapOperation tapCustom(String id, RequestMessage message, String keyFilter,
			String valueFilter, OperationCallback cb) {
		return new TapCustomOperationImpl(id, message, keyFilter, valueFilter, cb);
	}

	public TapOperation tapAck(Opcode opcode, int opaque, OperationCallback cb) {
		return new TapAckOperationImpl(opcode, opaque, cb);
	}
