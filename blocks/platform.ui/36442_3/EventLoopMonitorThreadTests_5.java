	private static final String UI_THREAD_FILTER =
			"org.eclipse.swt.internal.gtk.OS.gtk_dialog_run"
			+ ",org.eclipse.e4.ui.workbench.addons.dndaddon.DnDManager.startDrag";
	private static final String NONINTERESTING_THREAD_FILTER =
			"java.*"
			+ ",sun.*"
			+ ",org.eclipse.core.internal.jobs.WorkerPool.sleep"
			+ ",org.eclipse.core.internal.jobs.WorkerPool.startJob"
			+ ",org.eclipse.core.internal.jobs.Worker.run";
