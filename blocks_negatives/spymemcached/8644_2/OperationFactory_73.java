	/**
	 * Create a new sasl step operation.
	 */
	SASLStepOperation saslStep(String[] mech, byte[] challenge,
			String serverName, Map<String, ?> props, CallbackHandler cbh,
			OperationCallback cb);
