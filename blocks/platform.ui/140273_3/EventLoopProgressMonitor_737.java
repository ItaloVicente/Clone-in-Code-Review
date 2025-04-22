public class EventLoopProgressMonitor extends ProgressMonitorWrapper implements IProgressMonitorWithBlocking {
	private static int T_THRESH = 100;

	private static int T_MAX = 50;

	private long lastTime = System.currentTimeMillis();

	private String taskName;

	public EventLoopProgressMonitor(IProgressMonitor monitor) {
		super(monitor);
	}

	@Override
