	private OperationStatus mostRecentStatus = null;
	private int remaining=0;
	protected final OperationCallback originalCallback;

	/**
	 * Get a MultiOperationCallback over the given callback for the specified
	 * number of replicates.
	 *
	 * @param original the original callback
	 * @param todo how many complete() calls we expect before dispatching.
	 */
	public MultiOperationCallback(OperationCallback original, int todo) {
		originalCallback = original;
		remaining = todo;
	}
