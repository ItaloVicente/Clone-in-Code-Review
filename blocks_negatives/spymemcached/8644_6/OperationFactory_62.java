	/**
	 * Gets the value of a key and resets its timeout.
	 *
	 * @param key the key to get a value for and reset its timeout
	 * @param expiration the new expiration for the key
	 * @param cb the callback that will contain the result
	 * @return a new GATOperation
	 */
	GetAndTouchOperation getAndTouch(String key, int expiration,
			GetAndTouchOperation.Callback cb);
