	public OperationStatus getStatus() {
		if (status == null) {
			try {
				get();
			} catch (InterruptedException e) {
				status = new OperationStatus(false, "Interrupted");
				Thread.currentThread().isInterrupted();
			} catch (ExecutionException e) {
			    getLogger().warn("Error getting status of operation", e);
			}
		}
		return status;
	}

	public void set(T op, OperationStatus s) {
