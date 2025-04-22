    /**
     * The read/write lock for the global services.
     */
    private final ReadWriteLock globalServiceLock = new ReentrantReadWriteLock();

    /**
     * The read/write lock for the local services.
     */
    private final ReadWriteLock localServiceLock = new ReentrantReadWriteLock();

