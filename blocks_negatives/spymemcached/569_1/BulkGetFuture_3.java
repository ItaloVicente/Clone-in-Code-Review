	private Map<String, T> internalGet(long timeout, TimeUnit unit,
			Collection<Operation> timedoutOps) throws InterruptedException,
			ExecutionException {
		if (!latch.await(timeout, unit)) {
			for (Operation op : ops) {
				if (op.getState() != OperationState.COMPLETE) {
					timedoutOps.add(op);
				}
			}
		}
		for (Operation op : ops) {
			if (op.isCancelled()) {
				throw new ExecutionException(new RuntimeException("Cancelled"));
			}
			if (op.hasErrored()) {
				throw new ExecutionException(op.getException());
			}
		}
		Map<String, T> m = new HashMap<String, T>();
		for (Map.Entry<String, Future<T>> me : rvMap.entrySet()) {
			m.put(me.getKey(), me.getValue().get());
		}
		return m;
	}
