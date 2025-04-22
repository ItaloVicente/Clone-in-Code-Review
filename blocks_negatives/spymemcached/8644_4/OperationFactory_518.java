	/**
	 * Get a concatenation operation.
	 *
	 * @param catType the type of concatenation to perform.
	 * @param key the key
	 * @param casId the CAS value for an atomic compare-and-cat
	 * @param data the data to store
	 * @param cb a callback for reporting the status
	 * @return thew new ConcatenationOperation
	 */
	ConcatenationOperation cat(ConcatenationType catType, long casId,
			String key, byte[] data, OperationCallback cb);
