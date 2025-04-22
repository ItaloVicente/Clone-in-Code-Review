	/**
	 * Sends a tap ack message to the server.
	 *
	 * details on the tap protocol.
	 *
	 * @param opcode the opcode sent to the client by the server.
	 * @param opaque the opaque value sent to the client by the server.
	 * @param cb the callback for the tap stream.
	 * @return a tap ack operation.
	 */
	TapOperation tapAck(TapOpcode opcode, int opaque, OperationCallback cb);
