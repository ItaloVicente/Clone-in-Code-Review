	/**
	 * Creates a custom tap stream.
	 *
	 * details on the tap protocol.
	 *
	 * TAP connection names are optional, but allow for momentary interruptions
	 * in connection to automatically restart. TAP connection names also appear in
	 * TAP stats from the given server.
	 *
	 * @param id The name for the TAP connection
	 * @param message The tap message to send.
	 * @param cb The status callback.
	 * @return The tap operation used to create and handle the stream.
	 */
	TapOperation tapCustom(String id, RequestMessage message, OperationCallback cb);
