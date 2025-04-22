	/**
	 * Sends a tap dump message to the server.
	 *
	 * details on the tap protocol.
	 *
	 * @param id the name for the TAP connection
	 * @param cb the callback for the tap stream.
	 * @return a tap dump operation.
	 */
	TapOperation tapDump(String id, OperationCallback cb);
