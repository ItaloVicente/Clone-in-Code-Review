package org.eclipse.e4.ui.internal.workbench.swt;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ProgressMonitorWrapper;
import org.eclipse.e4.ui.workbench.IExceptionHandler;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;

public class EventLoopProgressMonitor extends ProgressMonitorWrapper implements
		IProgressMonitorWithBlocking {
	private static int T_THRESH = 100;

	private static int T_MAX = 50;

	private long lastTime = System.currentTimeMillis();

	private String taskName;

	private IExceptionHandler handler;

	public EventLoopProgressMonitor(IProgressMonitor monitor,
			IExceptionHandler handler) {
		super(monitor);
		this.handler = handler;
	}

	public void beginTask(String name, int totalWork) {
		super.beginTask(name, totalWork);
		taskName = name;
		runEventLoop();
	}

	public void clearBlocked() {
		Dialog.getBlockedHandler().clearBlocked();
	}

	public void done() {
		super.done();
		taskName = null;
		runEventLoop();
	}

	public void internalWorked(double work) {
		super.internalWorked(work);
		runEventLoop();
	}

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

		for (;;) {
			try {
				if (!disp.readAndDispatch()) {
					break;
				}
			} catch (Throwable e) {// Handle the exception the same way as the
				if (handler != null) {
					handler.handleException(e);
				}
				break;
			}

			if (System.currentTimeMillis() - t > T_MAX) {
				break;
			}
		}
	}

	public void setBlocked(IStatus reason) {
		Dialog.getBlockedHandler().showBlocked(this, reason, taskName);
	}

	public void setCanceled(boolean b) {
		super.setCanceled(b);
		taskName = null;
		runEventLoop();
	}

	public void setTaskName(String name) {
		super.setTaskName(name);
		taskName = name;
		runEventLoop();
	}

	public void subTask(String name) {
		if (taskName == null) {
			taskName = name;
		}
		super.subTask(name);
		runEventLoop();
	}

	public void worked(int work) {
		super.worked(work);
		runEventLoop();
	}

	protected String getTaskName() {
		return taskName;
	}
}
