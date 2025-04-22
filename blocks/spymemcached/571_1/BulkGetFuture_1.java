	public Map<String, T> get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		Collection<Operation> timedoutOps = new HashSet<Operation>();
		Map<String, T> ret = internalGet(timeout, unit, timedoutOps);
		if (timedoutOps.size() > 0) {
			throw new CheckedOperationTimeoutException("Operation timed out.",
					timedoutOps);
		}
		return ret;
	}
