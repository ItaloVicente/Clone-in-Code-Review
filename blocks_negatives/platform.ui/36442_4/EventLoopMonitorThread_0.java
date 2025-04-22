	private static final FilterHandler NON_INTERESTING_THREAD_FILTER = new FilterHandler(
			+ ",sun.*" //$NON-NLS-1$
			+ ",org.eclipse.core.internal.jobs.WorkerPool.sleep" //$NON-NLS-1$
			+ ",org.eclipse.core.internal.jobs.WorkerPool.startJob" //$NON-NLS-1$
			+ ",org.eclipse.core.internal.jobs.Worker.run" //$NON-NLS-1$
			+ ",org.eclipse.osgi.framework.eventmgr.EventManager$EventThread.getNextEvent" //$NON-NLS-1$
			+ ",org.eclipse.osgi.framework.eventmgr.EventManager$EventThread.run" //$NON-NLS-1$
			+ ",org.eclipse.equinox.internal.util.impl.tpt.timer.TimerImpl.run" //$NON-NLS-1$
			+ ",org.eclipse.equinox.internal.util.impl.tpt.threadpool.Executor.run"); //$NON-NLS-1$
