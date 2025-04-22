	private final int capacity;

	/**
	 * Create an ArrayOperationQueueFactory that creates blocking queues with
	 * the given capacity.
	 *
	 * @param cap maximum size of a queue produced by this factory
	 */
	public ArrayOperationQueueFactory(int cap) {
		super();
		capacity = cap;
	}
