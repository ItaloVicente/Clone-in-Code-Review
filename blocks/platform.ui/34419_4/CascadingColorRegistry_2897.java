package org.eclipse.ui.internal.testing;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.testing.TestableObject;

public class WorkbenchTestable extends TestableObject {

    private Display display;

    private IWorkbench workbench;

    private boolean oldAutomatedMode;

    private boolean oldIgnoreErrors;

    public WorkbenchTestable() {
    }

    public void init(Display display, IWorkbench workbench) {
        Assert.isNotNull(display);
        Assert.isNotNull(workbench);
        this.display = display;
        this.workbench = workbench;
        if (getTestHarness() != null) {
            Runnable runnable = new Runnable() {
                @Override
				public void run() {
					if ("true".equalsIgnoreCase(System.getProperty(PlatformUI.PLUGIN_ID + ".testsDisableWorkbenchAutoSave"))) { //$NON-NLS-1$ //$NON-NLS-2$
						if (WorkbenchTestable.this.workbench instanceof Workbench) {
							((Workbench) WorkbenchTestable.this.workbench).setEnableAutoSave(false);
						}
						Job.getJobManager().cancel(Workbench.WORKBENCH_AUTO_SAVE_JOB);
					}
                	if (!"false".equalsIgnoreCase(System.getProperty(PlatformUI.PLUGIN_ID + ".testsWaitForEarlyStartup"))) {  //$NON-NLS-1$ //$NON-NLS-2$
                		waitForEarlyStartup();
                	}
                    getTestHarness().runTests();
                }
            };
            new Thread(runnable, "WorkbenchTestable").start(); //$NON-NLS-1$
        }
    }

    private void waitForEarlyStartup() {
		try {
			Job.getJobManager().join(Workbench.EARLY_STARTUP_FAMILY, null);
		} catch (OperationCanceledException e) {
		} catch (InterruptedException e) {
		}
    }
    
    @Override
	public void testingStarting() {
        Assert.isNotNull(workbench);
        oldAutomatedMode = ErrorDialog.AUTOMATED_MODE;
        ErrorDialog.AUTOMATED_MODE = true;
        oldIgnoreErrors = SafeRunnable.getIgnoreErrors();
        SafeRunnable.setIgnoreErrors(true);
    }

    @Override
	public void runTest(Runnable testRunnable) {
        Assert.isNotNull(workbench);
        display.syncExec(testRunnable);
    }

    @Override
	public void testingFinished() {
        display.syncExec(new Runnable() {
            @Override
			public void run() {
                Assert.isTrue(workbench.close());
            }
        });
        ErrorDialog.AUTOMATED_MODE = oldAutomatedMode;
        SafeRunnable.setIgnoreErrors(oldIgnoreErrors);
    }
}
