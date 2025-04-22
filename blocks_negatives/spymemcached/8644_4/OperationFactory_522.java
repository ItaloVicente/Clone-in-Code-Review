	/**
	 * Creates a tap backfill stream.
	 *
	 * details on the tap protocol.
	 *
	 * TAP connection names are optional, but allow for momentary interruptions
	 * in connection to automatically restart. TAP connection names also appear in
	 * TAP stats from the given server.
	 *
	 * Note that according to the protocol, TAP backfill dates are advisory and the
	 * protocol guarantees at least data from specified date forward, but earlier
	 * mutations may be received.
	 *
	 * @param id The name for the TAP connection
	 * @param date The date to start backfill from.
	 * @param cb The status callback.
	 * @return The tap operation used to create and handle the stream.
	 */
	TapOperation tapBackfill(String id, long date, OperationCallback cb);
