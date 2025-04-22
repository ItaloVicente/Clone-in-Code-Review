
	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#copyInputQueue()
	 */
	public final void copyInputQueue() {
		Collection<Operation> tmp=new ArrayList<Operation>();

		inputQueue.drainTo(tmp, writeQ.remainingCapacity());

		writeQ.addAll(tmp);
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#destroyInputQueue()
	 */
	public Collection<Operation> destroyInputQueue() {
		Collection<Operation> rv=new ArrayList<Operation>();
		inputQueue.drainTo(rv);
		return rv;
	}

