public class EventLoopProgressMonitor extends ProgressMonitorWrapper implements
        IProgressMonitorWithBlocking {
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

    /**
     * @see IProgressMonitor#beginTask
     */
    @Override
