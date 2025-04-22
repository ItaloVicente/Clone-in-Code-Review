	@Override
	public TapOperation tapBackfill(String id, long date, OperationCallback cb) {
		throw new UnsupportedOperationException("Tap is not supported for ASCII" +
				" protocol");
	}

	@Override
	public TapOperation tapCustom(String id, RequestMessage message,
			OperationCallback cb) {
		throw new UnsupportedOperationException("Tap is not supported for ASCII" +
				" protocol");
	}

	@Override
	public TapOperation tapAck(TapOpcode opcode, int opaque, OperationCallback cb) {
		throw new UnsupportedOperationException("Tap is not supported for ASCII" +
				" protocol");
	}

