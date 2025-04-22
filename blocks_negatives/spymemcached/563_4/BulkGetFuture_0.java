	public Map<String, T> get(long timeout, TimeUnit unit)
		throws InterruptedException,
		ExecutionException, TimeoutException {
		if(!latch.await(timeout, unit)) {
			Collection<Operation> timedoutOps = new HashSet<Operation>();
			for(Operation op : ops) {
				if(op.getState() != OperationState.COMPLETE) {
					timedoutOps.add(op);
				}
			}
			throw new CheckedOperationTimeoutException("Operation timed out.",
					timedoutOps);
		}
		for(Operation op : ops) {
			if(op.isCancelled()) {
				throw new ExecutionException(
						new RuntimeException("Cancelled"));
			}
			if(op.hasErrored()) {
				throw new ExecutionException(op.getException());
			}
		}
		Map<String, T> m = new HashMap<String, T>();
		for (Map.Entry<String, Future<T>> me : rvMap.entrySet()) {
			m.put(me.getKey(), me.getValue().get());
		}
		return m;
	}
