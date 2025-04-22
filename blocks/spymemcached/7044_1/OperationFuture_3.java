	public OperationStatus getStatus() {
		if (status == null) {
			try {
				get();
			} catch (InterruptedException e) {
				status = new OperationStatus(false, "Interrupted");
				Thread.currentThread().isInterrupted();
			} catch (ExecutionException e) {

			}
		}
		return status;
	}

	public void set(T o, OperationStatus s) {
