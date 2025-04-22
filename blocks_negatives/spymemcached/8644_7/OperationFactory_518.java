	/**
	 * Create a get operation.
	 *
	 * @param keys the collection of keys to get
	 * @param cb the callback that will contain the results
	 * @return a new GetOperation
	 */
	GetOperation get(Collection<String> keys, GetOperation.Callback cb);
