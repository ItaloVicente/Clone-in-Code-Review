package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogBlockedHandler;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.progress.BlockedJobsDialog;

public class WorkbenchDialogBlockedHandler implements IDialogBlockedHandler {
    IProgressMonitor outerMonitor;

    int nestingDepth = 0;

    public WorkbenchDialogBlockedHandler() {
    }

    @Override
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

    @Override
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
                    blockingStatus, blockedName);
        }

    }

    @Override
	public void showBlocked(IProgressMonitor blocking, IStatus blockingStatus,
            String blockedName) {
        showBlocked(null, blocking, blockingStatus, blockedName);
    }
}
