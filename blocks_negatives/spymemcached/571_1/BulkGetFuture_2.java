    /*
     * get all or nothing: timeout exception is thrown if all the data could not
     * be retrieved
     * 
     * @see java.util.concurrent.Future#get(long, java.util.concurrent.TimeUnit)
     */
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
