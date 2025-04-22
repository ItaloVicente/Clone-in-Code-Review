	/**
	 * Create a store operation.
	 *
	 * @param storeType the type of store operation
	 * @param key the key to store
	 * @param flags the storage flags
	 * @param exp the expiration time
	 * @param data the data
	 * @param cb the status callback
	 * @return the new store operation
	 */
	StoreOperation store(StoreType storeType, String key, int flags, int exp,
			byte[] data, OperationCallback cb);
