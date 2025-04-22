	
	public String getKey() {
		return key;
	}
	
	public OperationStatus getStatus() {
		if (status == null) {
			try {
				get();
			} catch (InterruptedException e) {
				status = new OperationStatus(false, "Timed Out");
			} catch (ExecutionException e) {
				status = new OperationStatus(false, "Timed Out");
			}
		}
		return status;
	}
