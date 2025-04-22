	private final CountDownLatch latch;
	private final AtomicReference<T> objRef;
	protected OperationStatus status;
	private final long timeout;
	private Operation op;
	private final String key;

	public OperationFuture(String k, CountDownLatch l, long opTimeout) {
		this(k, l, new AtomicReference<T>(null), opTimeout);
	}

	public OperationFuture(String k, CountDownLatch l, AtomicReference<T> oref,
		long opTimeout) {
		super();
		latch=l;
		objRef=oref;
		status = null;
		timeout = opTimeout;
		key = k;
	}

	public boolean cancel(boolean ign) {
		assert op != null : "No operation";
		op.cancel();
		return op.getState() == OperationState.WRITING;
	}

	public T get() throws InterruptedException, ExecutionException {
		try {
			return get(timeout, TimeUnit.MILLISECONDS);
		} catch (TimeoutException e) {
			status = new OperationStatus(false, "Timed out");
			throw new RuntimeException(
				"Timed out waiting for operation", e);
		}
	}

	public T get(long duration, TimeUnit units)
		throws InterruptedException, TimeoutException, ExecutionException {
		if(!latch.await(duration, units)) {
			MemcachedConnection.opTimedOut(op);
				op.timeOut();
			}
			status = new OperationStatus(false, "Timed out");
			throw new CheckedOperationTimeoutException(
					"Timed out waiting for operation", op);
		} else {
		    MemcachedConnection.opSucceeded(op);
		}
		if(op != null && op.hasErrored()) {
			status = new OperationStatus(false, op.getException().getMessage());
			throw new ExecutionException(op.getException());
		}
		if(isCancelled()) {
			throw new ExecutionException(new RuntimeException("Cancelled"));
		}
                if(op != null && op.isTimedOut()) {
						status = new OperationStatus(false, "Timed out");
                        throw new ExecutionException(new CheckedOperationTimeoutException("Operation timed out.", op));
                }

		return objRef.get();
	}

	public String getKey() {
		return key;
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

	public void set(T o, OperationStatus s) {
		objRef.set(o);
		status = s;
	}

	public void setOperation(Operation to) {
		op=to;
	}

	public boolean isCancelled() {
		assert op != null : "No operation";
		return op.isCancelled();
	}

	public boolean isDone() {
		assert op != null : "No operation";
		return latch.getCount() == 0 ||
			op.isCancelled() || op.getState() == OperationState.COMPLETE;
	}
