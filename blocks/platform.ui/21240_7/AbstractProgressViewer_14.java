package org.eclipse.e4.ui.progress.e4new;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.e4.ui.progress.internal.BlockedJobsDialog;
import org.eclipse.jface.dialogs.IDialogBlockedHandler;
import org.eclipse.swt.widgets.Shell;

public class WorkbenchDialogBlockedHandler implements IDialogBlockedHandler {
    IProgressMonitor outerMonitor;

    int nestingDepth = 0;

    public WorkbenchDialogBlockedHandler() {
    }

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
                    blockingStatus, blockedName);
        }

    }

    public void showBlocked(IProgressMonitor blocking, IStatus blockingStatus,
            String blockedName) {
        showBlocked(null, blocking, blockingStatus, blockedName);
    }
}
