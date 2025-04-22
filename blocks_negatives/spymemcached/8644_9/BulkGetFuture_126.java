	private final Map<String, Future<T>> rvMap;
	private final Collection<Operation> ops;
	private final CountDownLatch latch;
	private OperationStatus status;
	private boolean cancelled=false;
	private boolean timeout = false;

	public BulkGetFuture(Map<String, Future<T>> m,
			Collection<Operation> getOps, CountDownLatch l) {
		super();
		rvMap = m;
		ops = getOps;
		latch=l;
		status = null;
	}

	public boolean cancel(boolean ign) {
		boolean rv=false;
		for(Operation op : ops) {
			rv |= op.getState() == OperationState.WRITING;
			op.cancel();
		}
		for (Future<T> v : rvMap.values()) {
			v.cancel(ign);
		}
		cancelled=true;
		status = new OperationStatus(false, "Cancelled");
		return rv;
	}

	public Map<String, T> get()
		throws InterruptedException, ExecutionException {
		try {
			return get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (TimeoutException e) {
			throw new RuntimeException("Timed out waiting forever", e);
		}
	}

    /* (non-Javadoc)
     * @see net.spy.memcached.internal.BulkFuture#getSome(long, java.util.concurrent.TimeUnit)
     */
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
