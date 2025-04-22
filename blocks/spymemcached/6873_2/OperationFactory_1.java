
	TapOperation tapBackfill(String id, Date date, String keyFilter,
			String valueFilter, OperationCallback cb);

	TapOperation tapCustom(String id, RequestMessage message, String keyFilter, 
			String valueFilter, OperationCallback cb);

	TapOperation tapAck(Opcode opcode, int opaque,
			OperationCallback cb);
