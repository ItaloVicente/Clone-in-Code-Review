
	TapOperation tapBackfill(String id, long date, OperationCallback cb);

	TapOperation tapCustom(String id, RequestMessage message, OperationCallback cb);

	TapOperation tapAck(TapOpcode opcode, int opaque,
			OperationCallback cb);
