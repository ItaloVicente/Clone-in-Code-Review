    /**
     * refactored code common to both get(long, TimeUnit)
     * and getSome(long, TimeUnit)
     *
     * @param to
     * @param unit
     * @param timedoutOps
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private Map<String, T> internalGet(long to, TimeUnit unit,
            Collection<Operation> timedoutOps) throws InterruptedException,
            ExecutionException {
        if (!latch.await(to, unit)) {
            for (Operation op : ops) {
                if (op.getState() != OperationState.COMPLETE) {
                    MemcachedConnection.opTimedOut(op);
                    timedoutOps.add(op);
                } else {
                    MemcachedConnection.opSucceeded(op);
                }
            }
        }
        for (Operation op : ops) {
            if (op.isCancelled()) {
		status = new OperationStatus(false, "Cancelled");
                throw new ExecutionException(new RuntimeException("Cancelled"));
            }
            if (op.hasErrored()) {
		status = new OperationStatus(false, op.getException().getMessage());
                throw new ExecutionException(op.getException());
            }
        }
        Map<String, T> m = new HashMap<String, T>();
        for (Map.Entry<String, Future<T>> me : rvMap.entrySet()) {
            m.put(me.getKey(), me.getValue().get());
