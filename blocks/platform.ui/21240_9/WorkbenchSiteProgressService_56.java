package org.eclipse.e4.ui.progress.internal;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.progress.IProgressService;
import org.eclipse.jface.dialogs.IDialogBlockedHandler;
import org.eclipse.swt.widgets.Shell;

@Creatable
@Singleton
public class WorkbenchDialogBlockedHandler implements IDialogBlockedHandler {
    IProgressMonitor outerMonitor;
    
    @Inject
    @Optional
    IProgressService progressService;
    
    @Inject
    @Optional
    ProgressManager progressManager;
    
    @Inject
    @Optional
    FinishedJobs finishedJobs;
    
    @Inject
    @Optional
    ProgressViewUpdater progressViewUpdater;
    
    
    

    int nestingDepth = 0;

    public void clearBlocked() {
        if (nestingDepth == 0) {
			return;
		}

        nestingDepth--;

        if (nestingDepth <= 0) {
            BlockedJobsDialog.clear(outerMonitor);
            outerMonitor = null;
            nestingDepth = 0;
        }

    }

    public void showBlocked(Shell parentShell,
            IProgressMonitor blockingMonitor, IStatus blockingStatus,
            String blockedName) {

        nestingDepth++;
        if (outerMonitor == null) {
            outerMonitor = blockingMonitor;
            if (blockedName == null && parentShell != null) {
				blockedName = parentShell.getText();
			}
			BlockedJobsDialog.createBlockedDialog(parentShell, blockingMonitor,
			        blockingStatus, blockedName, progressService, finishedJobs,
			        progressViewUpdater, progressManager);
        }

    }

    public void showBlocked(IProgressMonitor blocking, IStatus blockingStatus,
            String blockedName) {
        showBlocked(null, blocking, blockingStatus, blockedName);
    }
}
