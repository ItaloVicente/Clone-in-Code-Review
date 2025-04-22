	/* (non-Javadoc)
	 * @see net.spy.memcached.ops.OperationQueueFactory#create()
	 */
	public BlockingQueue<Operation> create() {
		return new ArrayBlockingQueue<Operation>(capacity);
	}
