package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ProgressMonitorWrapper;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.ExceptionHandler;

public class EventLoopProgressMonitor extends ProgressMonitorWrapper implements
        IProgressMonitorWithBlocking {
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

        ExceptionHandler handler = ExceptionHandler.getInstance();

        for (;;) {
            try {
                if (!disp.readAndDispatch()) {
					break;
				}
            } catch (Throwable e) {//Handle the exception the same way as the workbench
                handler.handleException(e);
                break;
            }

            if (System.currentTimeMillis() - t > T_MAX) {
                break;
            }
        }
    }

    @Override
	public void setBlocked(IStatus reason) {
        Dialog.getBlockedHandler().showBlocked(this, reason, taskName);
    }

    @Override
	public void setCanceled(boolean b) {
        super.setCanceled(b);
        taskName = null;
        runEventLoop();
    }

    @Override
	public void setTaskName(String name) {
        super.setTaskName(name);
        taskName = name;
        runEventLoop();
    }

    @Override
	public void subTask(String name) {
        if (taskName == null) {
			taskName = name;
		}
        super.subTask(name);
        runEventLoop();
    }

    @Override
	public void worked(int work) {
        super.worked(work);
        runEventLoop();
    }

    protected String getTaskName() {
        return taskName;
    }
}
