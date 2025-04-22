    /**
     * this method does not throw timeout exception. Instead, if timeout is
     * reached, it returns what's there and sets isTimeout flag. This allows to
     * get partial data when timeout is reached.
     * 
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
