	private static final String UI_THREAD_FILTER =
			"org.eclipse.swt.internal.gtk.OS.gtk_dialog_run"
			+ ",org.eclipse.e4.ui.workbench.addons.dndaddon.DnDManager.startDrag";
	private static final String NONINTERESTING_THREAD_FILTER =
			"java.*"
			+ ",sun.*"
			+ ",org.eclipse.core.internal.jobs.WorkerPool.sleep"
			+ ",org.eclipse.core.internal.jobs.WorkerPool.startJob"
			+ ",org.eclipse.core.internal.jobs.Worker.run";
	/* NOTE: All time-related values in this class are in milliseconds. */
	private static final int FREEZE_THRESHOLD_MS = 100;
	private static final int SAMPLE_INTERVAL_MS = FREEZE_THRESHOLD_MS * 2 / 3;
	private static final int MIN_STACK_TRACES = 5;
	private static final int MAX_STACK_TRACES = 11;
	private static final int MIN_MAX_STACK_TRACE_DELTA = MAX_STACK_TRACES - MIN_STACK_TRACES;
