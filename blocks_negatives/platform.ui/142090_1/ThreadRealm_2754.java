    private Thread thread;

    private final LinkedList<Runnable> queue = new LinkedList<Runnable>();

    private volatile boolean block;

    /**
     * Initializes the realm.
     *
     * @param thread
     */
    public synchronized void init(Thread thread) {
        if (thread == null) {
        }
        Assert.isTrue(this.thread == null, "Realm can only be initialized once.");

        this.thread = thread;
    }

    /**
     * @return <code>true</code> if the current thread is the thread for
     *         the realm
     */
    @Override
