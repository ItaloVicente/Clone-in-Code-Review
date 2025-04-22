	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#transitionWriteItem()
	 */
	public final void transitionWriteItem() {
		Operation op=removeCurrentWriteOp();
		assert op != null : "There is no write item to transition";
		getLogger().debug("Finished writing %s", op);
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#optimize()
	 */
	protected abstract void optimize();

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#getCurrentReadOp()
	 */
	public final Operation getCurrentReadOp() {
		return readQ.peek();
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#removeCurrentReadOp()
	 */
	public final Operation removeCurrentReadOp() {
		return readQ.remove();
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#getCurrentWriteOp()
	 */
	public final Operation getCurrentWriteOp() {
		return optimizedOp == null ? writeQ.peek() : optimizedOp;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#removeCurrentWriteOp()
	 */
	public final Operation removeCurrentWriteOp() {
		Operation rv=optimizedOp;
		if(rv == null) {
			rv=writeQ.remove();
		} else {
			optimizedOp=null;
		}
		return rv;
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#hasReadOp()
	 */
	public final boolean hasReadOp() {
		return !readQ.isEmpty();
	}

	/* (non-Javadoc)
	 * @see net.spy.memcached.MemcachedNode#hasWriteOp()
	 */
	public final boolean hasWriteOp() {
		return !(optimizedOp == null && writeQ.isEmpty());
	}

