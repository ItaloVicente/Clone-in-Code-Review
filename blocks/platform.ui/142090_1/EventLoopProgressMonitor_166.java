	private static int T_THRESH = 100;

	private static int T_MAX = 50;

	private long lastTime = System.currentTimeMillis();

	private String taskName;

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

