	/**
	 * Resets a keys expiration time
	 * @param key The key whose expiration time is to be reset.
	 * @param expiration The new expiration time for the key
	 * @param cb The status callback
	 * @return A touch operation
	 */
	KeyedOperation touch(String key, int expiration, OperationCallback cb);
