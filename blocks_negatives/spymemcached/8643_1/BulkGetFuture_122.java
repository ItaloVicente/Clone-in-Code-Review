	public OperationStatus getStatus() {
		if (status == null) {
			try {
				get();
			} catch (InterruptedException e) {
				status = new OperationStatus(false, "Interrupted");
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				return status;
			}
		}
		return status;
	}

	public void setStatus(OperationStatus s) {
		status = s;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public boolean isDone() {
		return latch.getCount() == 0;
	}

	/* set to true if timeout was reached.
	 *
	 * @see net.spy.memcached.internal.BulkFuture#isTimeout()
	 */
	public boolean isTimeout() {
		return timeout;
	}
}
