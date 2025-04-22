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

    public Map<String, T> get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        Collection<Operation> timedoutOps = new HashSet<Operation>();
        Map<String, T> ret = internalGet(timeout, unit, timedoutOps);
        if (timedoutOps.size() > 0) {
            this.timeout = true;
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
