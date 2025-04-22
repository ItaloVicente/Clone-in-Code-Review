	
	public Map<String, T> getSome(long to, TimeUnit unit)
			throws InterruptedException, ExecutionException {
		Collection<Operation> timedoutOps = new HashSet<Operation>();
		Map<String, T> ret = internalGet(to, unit, timedoutOps);
		if (timedoutOps.size() > 0) {
			timeout = true;
			LoggerFactory.getLogger(getClass()).warn(
					new CheckedOperationTimeoutException(
							"Operation timed out: ", timedoutOps).getMessage());
		}
		return ret;

	}
