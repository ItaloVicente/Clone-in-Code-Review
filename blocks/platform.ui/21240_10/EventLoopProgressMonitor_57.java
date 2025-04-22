package org.eclipse.e4.ui.progress.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.e4.ui.internal.workbench.swt.CSSConstants;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.progress.IProgressService;
import org.eclipse.e4.ui.progress.IWorkbenchSiteProgressService;
import org.eclipse.e4.ui.progress.WorkbenchJob;
import org.eclipse.e4.ui.progress.internal.legacy.PlatformUI;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class WorkbenchSiteProgressService implements
        IWorkbenchSiteProgressService, IJobBusyListener {
    MPart part;
    private ProgressManager progressManager;
    private IProgressService progressService;

	private Map<Job, Boolean> busyJobs = new HashMap<Job, Boolean>();

    private Object busyLock = new Object();

    IPropertyChangeListener[] changeListeners = new IPropertyChangeListener[0];

    private Cursor waitCursor;

    private int waitCursorJobCount;
    
    private Object waitCursorLock = new Object();
    
    private SiteUpdateJob updateJob;

	private int busyCount = 0;

    public class SiteUpdateJob extends WorkbenchJob {
        private boolean busy;

        Object lock = new Object();

        void setBusy(boolean cursorState) {
            synchronized (lock) {
                busy = cursorState;
            }
        }

        private SiteUpdateJob() {
            super(ProgressMessages.WorkbenchSiteProgressService_CursorJob);
        }

        private Cursor getWaitCursor(Display display) {
            if (waitCursor == null) {
                waitCursor = new Cursor(display, SWT.CURSOR_APPSTARTING);
            }
            return waitCursor;
        }

        public IStatus runInUIThread(IProgressMonitor monitor) {
			Control control = (Control) part.getWidget();
            if (control == null || control.isDisposed()) {
				return Status.CANCEL_STATUS;
			}
            synchronized (lock) {
                Cursor cursor = null;
                if (waitCursorJobCount !=0) {
					cursor = getWaitCursor(control.getDisplay());
				}
                control.setCursor(cursor);
				showBusy(busy);
            }
            return Status.OK_STATUS;
        }

        void clearCursors() {
            if (waitCursor != null) {
                waitCursor.dispose();
                waitCursor = null;
            }
        }
        
    }

	public WorkbenchSiteProgressService(final MPart part,
	        ProgressManager progressManager, IProgressService progressService) {
        this.part = part;
        this.progressManager = progressManager;
        this.progressService = progressService;
        updateJob = new SiteUpdateJob();
        updateJob.setSystem(true);
    }

    public void dispose() {
        if (updateJob != null) {
			updateJob.cancel();
		}

        progressManager.removeListener(this);
		showBusy(false);

        if (waitCursor == null) {
			return;
		}
        waitCursor.dispose();
        waitCursor = null;
    }

    public void busyCursorWhile(IRunnableWithProgress runnable)
            throws InvocationTargetException, InterruptedException {
        getWorkbenchProgressService().busyCursorWhile(runnable);
    }

    public void schedule(Job job, long delay, boolean useHalfBusyCursor) {
        job.addJobChangeListener(getJobChangeListener(useHalfBusyCursor));
        job.schedule(delay);
    }

    public void schedule(Job job, long delay) {
        schedule(job, delay, false);
    }

    public void schedule(Job job) {
        schedule(job, 0L, false);
    }

    public void showBusyForFamily(Object family) {
        progressManager.addListenerToFamily(family, this);
    }

	public IJobChangeListener getJobChangeListener(final boolean useHalfBusyCursor) {
		return new JobChangeAdapter() {

			public void aboutToRun(IJobChangeEvent event) {
				incrementBusy(event.getJob(), useHalfBusyCursor);
			}

			public void done(IJobChangeEvent event) {
				Job job = event.getJob();
				decrementBusy(job);
				job.removeJobChangeListener(this);
			}
		};
	}

    public void decrementBusy(Job job) {
		Object halfBusyCursorState;
        synchronized (busyLock) {
			halfBusyCursorState = busyJobs.remove(job);
			if (halfBusyCursorState == null) {
				return;
			}
		}
		if (halfBusyCursorState == Boolean.TRUE) {
			synchronized (waitCursorLock) {
				waitCursorJobCount--;
			}
        }
        try {
        	decrementBusy();
        } catch (Exception ex) {
        }
    }

    public void incrementBusy(Job job) {
		incrementBusy(job, false);
	}

	private void incrementBusy(Job job, boolean useHalfBusyCursor) {
		Object halfBusyCursorState;
        synchronized (busyLock) {
			halfBusyCursorState = busyJobs.get(job);
			if (useHalfBusyCursor || halfBusyCursorState != Boolean.TRUE)
				busyJobs.put(job, Boolean.valueOf(useHalfBusyCursor));
        }
		if (useHalfBusyCursor && halfBusyCursorState != Boolean.TRUE) {
			synchronized (waitCursorLock) {
				waitCursorJobCount++;
			}
		}
		if (halfBusyCursorState != null) {
			if (useHalfBusyCursor && halfBusyCursorState == Boolean.FALSE) {
				synchronized (busyLock) {
					updateJob.setBusy(true);
				}
				if (PlatformUI.isWorkbenchRunning()) {
					updateJob.schedule(100);
				} else {
					updateJob.cancel();
				}
			}
			return;
		}
        incrementBusy();
    }

    public void warnOfContentChange() {
		if (!part.getTags().contains(CSSConstants.CSS_CONTENT_CHANGE_CLASS)) {
			part.getTags().add(CSSConstants.CSS_CONTENT_CHANGE_CLASS);
		}
    }

    public void showInDialog(Shell shell, Job job) {
        getWorkbenchProgressService().showInDialog(shell, job);
    }

    protected IProgressService getWorkbenchProgressService() {
        return progressService;
    }

    public void run(boolean fork, boolean cancelable,
            IRunnableWithProgress runnable) throws InvocationTargetException,
            InterruptedException {
        getWorkbenchProgressService().run(fork, cancelable, runnable);
    }

    public void runInUI(IRunnableContext context,
            IRunnableWithProgress runnable, ISchedulingRule rule)
            throws InvocationTargetException, InterruptedException {
        getWorkbenchProgressService().runInUI(context, runnable, rule);
    }

    public int getLongOperationTime() {
        return getWorkbenchProgressService().getLongOperationTime();
    }

    public void registerIconForFamily(ImageDescriptor icon, Object family) {
        getWorkbenchProgressService().registerIconForFamily(icon, family);
    }

    public Image getIconFor(Job job) {
        return getWorkbenchProgressService().getIconFor(job);
    }

    public void incrementBusy() {
		synchronized (busyLock) {
			this.busyCount++;
			if (busyCount != 1) {
				return;
			}
			updateJob.setBusy(true);
		}
		if (PlatformUI.isWorkbenchRunning()) {
			updateJob.schedule(100);
		} else {
			updateJob.cancel();
		}
    }
	public void decrementBusy() {
		synchronized (busyLock) {
			Assert
					.isTrue(
							busyCount > 0,
							"Ignoring unexpected call to IWorkbenchSiteProgressService.decrementBusy().  This might be due to an earlier call to this method."); //$NON-NLS-1$
			this.busyCount--;
			if (busyCount != 0) {
				return;
			}
			updateJob.setBusy(false);
		}
		if (PlatformUI.isWorkbenchRunning()) {
			updateJob.schedule(100);
		} else {
			updateJob.cancel();
		}
	}
	
	public SiteUpdateJob getUpdateJob() {
		return updateJob;
	}

	protected void showBusy(boolean busy) {
		if (busy) {
			part.getTags().add(CSSConstants.CSS_BUSY_CLASS);
		} else {
			part.getTags().remove(CSSConstants.CSS_BUSY_CLASS);
		}
	}
}
