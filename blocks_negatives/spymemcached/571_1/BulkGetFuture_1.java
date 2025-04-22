    /*
     * Does not throw timeout exception. If timeout is reached, returns the
     * partially retrieved data. If all data was retrieved in time, behavior is
     * the same as for get(long, TimeUnit)
     * 
     * @see net.spy.memcached.internal.BulkFuture#getSome(long,
     * java.util.concurrent.TimeUnit)
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
