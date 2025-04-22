	/**
	 * Shut down immediately.
	 */
	public void shutdown() {
		shutdown(-1, TimeUnit.MILLISECONDS);
	}

	/**
	 * Shut down this client gracefully.
	 *
	 * @param timeout the amount of time for shutdown
	 * @param unit the TimeUnit for the timeout
	 * @return result of the shutdown request
	 */
	public boolean shutdown(long timeout, TimeUnit unit) {
		if(shuttingDown) {
			getLogger().info("Suppressing duplicate attempt to shut down");
			return false;
		}
		shuttingDown=true;
		String baseName=conn.getName();
		conn.setName(baseName + " - SHUTTING DOWN");
		boolean rv=false;
		try {
			if(timeout > 0) {
				conn.setName(baseName + " - SHUTTING DOWN (waiting)");
				rv=waitForQueues(timeout, unit);
			}
		} finally {
			try {
				conn.setName(baseName + " - SHUTTING DOWN (telling client)");
				conn.shutdown();
				conn.setName(baseName + " - SHUTTING DOWN (informed client)");
				tcService.shutdown();
				if (this.configurationProvider != null) {
					this.configurationProvider.shutdown();
				}
			} catch (IOException e) {
				getLogger().warn("exception while shutting down", e);
			}
		}
		return rv;
	}

	/**
	 * Wait for the queues to die down.
	 *
	 * @param timeout the amount of time time for shutdown
	 * @param unit the TimeUnit for the timeout
	 * @return result of the request for the wait
	 * @throws IllegalStateException in the rare circumstance where queue
	 *         is too full to accept any more requests
	 */
	public boolean waitForQueues(long timeout, TimeUnit unit) {
		CountDownLatch blatch = broadcastOp(new BroadcastOpFactory(){
			public Operation newOp(final MemcachedNode n,
					final CountDownLatch latch) {
				return opFact.noop(
						new OperationCallback() {
							public void complete() {
								latch.countDown();
							}
							public void receivedStatus(OperationStatus s) {
							}
						});
			}}, conn.getLocator().getAll(), false);
		try {
			return blatch.await(timeout, unit);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted waiting for queues", e);
		}
	}

	CountDownLatch broadcastOp(final BroadcastOpFactory of) {
		return broadcastOp(of, conn.getLocator().getAll(), true);
	}

	CountDownLatch broadcastOp(final BroadcastOpFactory of,
			Collection<MemcachedNode> nodes) {
		return broadcastOp(of, nodes, true);
	}

	private CountDownLatch broadcastOp(BroadcastOpFactory of,
			Collection<MemcachedNode> nodes,
			boolean checkShuttingDown) {
		if(checkShuttingDown && shuttingDown) {
			throw new IllegalStateException("Shutting down");
		}
		return conn.broadcastOperation(of, nodes);
	}
