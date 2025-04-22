package org.eclipse.ui.progress;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.UIStats;
import org.eclipse.ui.internal.progress.ProgressMessages;

public abstract class UIJob extends Job {
    private Display cachedDisplay;

    public UIJob(String name) {
        super(name);
    }

    public UIJob(Display jobDisplay, String name) {
        this(name);
        setDisplay(jobDisplay);
    }

    public static IStatus errorStatus(Throwable exception) {
        return WorkbenchPlugin.getStatus(exception); 
    }

    @Override
	public final IStatus run(final IProgressMonitor monitor) {
        if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
        Display asyncDisplay = getDisplay();
        if (asyncDisplay == null || asyncDisplay.isDisposed()) {
            return Status.CANCEL_STATUS;
        }
        asyncDisplay.asyncExec(new Runnable() {
            @Override
			public void run() {
                IStatus result = null;
                Throwable throwable = null;
                try {
                    setThread(Thread.currentThread());
                    if (monitor.isCanceled()) {
						result = Status.CANCEL_STATUS;
					} else {
                       	UIStats.start(UIStats.UI_JOB, getName());
                        result = runInUIThread(monitor);
                    }

                } catch(Throwable t){
                	throwable = t;
                } finally {
               		UIStats.end(UIStats.UI_JOB, UIJob.this, getName());
                    if (result == null) {
						result = new Status(IStatus.ERROR,
                                PlatformUI.PLUGIN_ID, IStatus.ERROR,
                                ProgressMessages.InternalError,
                                throwable);
					}
                    done(result);
                }
            }
        });
        return Job.ASYNC_FINISH;
    }

    public abstract IStatus runInUIThread(IProgressMonitor monitor);

    public void setDisplay(Display runDisplay) {
        Assert.isNotNull(runDisplay);
        cachedDisplay = runDisplay;
    }

    public Display getDisplay() {
        if (cachedDisplay == null && PlatformUI.isWorkbenchRunning()) {
			return PlatformUI.getWorkbench().getDisplay();
		}
        return cachedDisplay;
    }
}
