    /**
     * Threshold for how often the event loop is spun, in ms.
     */
    private static int T_THRESH = 100;

    /**
     * Maximum amount of time to spend processing events, in ms.
     */
    private static int T_MAX = 50;

    /**
     * Last time the event loop was spun.
     */
    private long lastTime = System.currentTimeMillis();

    /**
     * The task name is the name of the current task
     * in the event loop.
     */
    private String taskName;

    /**
     * Constructs a new instance of the receiver and forwards to monitor.
     * @param monitor
     */
    public EventLoopProgressMonitor(IProgressMonitor monitor) {
        super(monitor);
    }

    @Override
    public void beginTask(String name, int totalWork) {
        super.beginTask(name, totalWork);
        taskName = name;
        runEventLoop();
    }

    @Override
    public void clearBlocked() {
        Dialog.getBlockedHandler().clearBlocked();
    }

     @Override
    public void done() {
        super.done();
        taskName = null;
        runEventLoop();
    }

    @Override
    public void internalWorked(double work) {
        super.internalWorked(work);
        runEventLoop();
    }

    @Override
    public boolean isCanceled() {
        runEventLoop();
        return super.isCanceled();
    }

    /**
     * Runs an event loop.
     */
    private void runEventLoop() {
        long t = System.currentTimeMillis();
        if (t - lastTime < T_THRESH) {
            return;
        }
        lastTime = t;
        Display disp = Display.getDefault();
        if (disp == null) {
            return;
        }

