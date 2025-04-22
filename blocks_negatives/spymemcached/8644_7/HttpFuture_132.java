	public boolean cancel(boolean c) {
		op.cancel();
		return true;
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		try {
			return get(timeout, TimeUnit.MILLISECONDS);
		} catch (TimeoutException e) {
			status = new OperationStatus(false, "Timed out");
			throw new RuntimeException(
				"Timed out waiting for operation", e);
		}
	}

	@Override
	public T get(long duration, TimeUnit units) throws InterruptedException,
			ExecutionException, TimeoutException {
		if(!latch.await(duration, units)) {
			if (op != null) {
				op.timeOut();
			}
			status = new OperationStatus(false, "Timed out");
			throw new TimeoutException(
					"Timed out waiting for operation");
		}

		if(op != null && op.hasErrored()) {
			status = new OperationStatus(false, op.getException().getMessage());
			throw new ExecutionException(op.getException());
		}

		if(op.isCancelled()) {
			status = new OperationStatus(false, "Operation Cancelled");
			throw new ExecutionException(new RuntimeException("Cancelled"));
		}

		if(op != null && op.isTimedOut()) {
			status = new OperationStatus(false, "Timed out");
            throw new ExecutionException(new OperationTimeoutException("Operation timed out."));
		}

		return objRef.get();
	}

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
		objRef.set(op);
		status = s;
	}

	@Override
	public boolean isDone() {
		assert op != null : "No operation";
		return latch.getCount() == 0 ||
			op.isCancelled() || op.hasErrored();
	}

	public void setOperation(HttpOperation to) {
		this.op = to;
	}

	@Override
	public boolean isCancelled() {
		assert op != null : "No operation";
		return op.isCancelled();
	}
