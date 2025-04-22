package org.eclipse.e4.ui.progress;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.progress.e4new.ExternalServices;
import org.eclipse.e4.ui.progress.internal.ProgressMessages;
import org.eclipse.e4.ui.progress.legacy.PlatformUI;
import org.eclipse.e4.ui.progress.legacy.StatusUtil;
import org.eclipse.swt.widgets.Display;

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
        return getStatus(exception);
    }

    public final IStatus run(final IProgressMonitor monitor) {
        if (monitor.isCanceled()) {
            return Status.CANCEL_STATUS;
        }
        UISynchronize uiSynchronize = getUiSynchronize();
        if (uiSynchronize == null) {
            return Status.CANCEL_STATUS;
        }
        uiSynchronize.asyncExec(new Runnable() {
            public void run() {
                IStatus result = null;
                Throwable throwable = null;
                try {
                    setThread(Thread.currentThread());
                    if (monitor.isCanceled()) {
						result = Status.CANCEL_STATUS;
					} else {
                        result = runInUIThread(monitor);
                    }

                } catch(Throwable t){
                	throwable = t;
                } finally {
                    if (result == null) {
						result = new Status(IStatus.ERROR,
                                IProgressConstants.PLUGIN_ID, IStatus.ERROR,
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
        if (cachedDisplay == null) {
            cachedDisplay = ExternalServices.getDisplay();
        }
        if (cachedDisplay == null) {
            cachedDisplay = Display.getCurrent();
        }
        if (cachedDisplay == null) {
            cachedDisplay = Display.getDefault();
        }
        return cachedDisplay;
    }

    public static IStatus getStatus(Throwable t) {
        String message = StatusUtil.getLocalizedMessage(t);

        return newError(message, t);
    }

    public static IStatus newError(String message, Throwable t) {
        String pluginId = IProgressConstants.PLUGIN_ID;
        int errorCode = IStatus.OK;

        if (t instanceof CoreException) {
            CoreException ce = (CoreException) t;
            pluginId = ce.getStatus().getPlugin();
            errorCode = ce.getStatus().getCode();
        }

        return new Status(IStatus.ERROR, pluginId, errorCode, message,
                StatusUtil.getCause(t));
    }
    
    protected UISynchronize getUiSynchronize() {
        return ExternalServices.getUiSynchronize();
    }
}
