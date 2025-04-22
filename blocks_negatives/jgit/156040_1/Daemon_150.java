    /**
     * Configures a new daemon for the specified network address. The daemon will not attempt to bind to an address or
     * accept connections until a call to {@link #start()}.
     * @param addr address to listen for connections on. If null, any available port will be chosen on all network
     * interfaces.
     * @param acceptThreadPool source of threads for waiting for inbound socket connections. Every time the daemon is started or
     * restarted, a new task will be submitted to this pool. When the daemon is stopped, the task completes.
     */
    public Daemon(final InetSocketAddress addr,
                  final Executor acceptThreadPool,
                  final ExecutorService executorService,
                  final KetchLeaderCache leaders) {
        myAddress = addr;
        this.acceptThreadPool = checkNotNull("acceptThreadPool",
                                             acceptThreadPool);
