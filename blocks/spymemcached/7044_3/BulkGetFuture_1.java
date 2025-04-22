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

