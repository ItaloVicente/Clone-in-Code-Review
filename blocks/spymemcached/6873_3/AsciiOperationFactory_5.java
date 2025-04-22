	@Override
	public TapOperation tapBackfill(String id, Date date, String keyFilter,
			String valueFilter, OperationCallback cb) {
		throw new UnsupportedOperationException("Tap is not supported for ascii");
	}

	@Override
	public TapOperation tapCustom(String id, RequestMessage message,
			String keyFilter, String valueFilter, OperationCallback cb) {
		throw new UnsupportedOperationException("Tap is not supported for ascii");
	}

	@Override
	public TapOperation tapAck(Opcode opcode, int opaque, OperationCallback cb) {
		throw new UnsupportedOperationException("Tap is not supported for ascii");
	}

