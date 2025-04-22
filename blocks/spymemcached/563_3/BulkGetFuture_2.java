			throws InterruptedException, ExecutionException, TimeoutException {
		Collection<Operation> timedoutOps = new HashSet<Operation>();
		Map<String, T> ret = internalGet(timeout, unit, timedoutOps);
		if (timedoutOps.size() > 0) {
			throw new CheckedOperationTimeoutException("Operation timed out.",
					timedoutOps);
		}
		return ret;
	}

	private Map<String, T> internalGet(long timeout, TimeUnit unit,
			Collection<Operation> timedoutOps) throws InterruptedException,
			ExecutionException {
		if (!latch.await(timeout, unit)) {
			for (Operation op : ops) {
				if (op.getState() != OperationState.COMPLETE) {
