	/**
	 * Create a CAS operation.
	 *
	 * @param key the key to store
	 * @param casId the CAS identifier value (from a gets operation)
	 * @param flags the storage flags
	 * @param exp the expiration time
	 * @param data the data
	 * @param cb the status callback
	 * @return the new store operation
	 */
	CASOperation cas(StoreType t, String key, long casId, int flags,
			int exp, byte[] data, OperationCallback cb);
