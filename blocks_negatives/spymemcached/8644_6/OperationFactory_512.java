	/**
	 * Create a NOOP operation.
	 *
	 * @param cb the operation callback
	 * @return the new NoopOperation
	 */
	NoopOperation noop(OperationCallback cb);

	/**
	 * Create a deletion operation.
	 *
	 * @param key the key to delete
	 * @param operationCallback the status callback
	 * @return the new DeleteOperation
	 */
	DeleteOperation delete(String key, OperationCallback operationCallback);
