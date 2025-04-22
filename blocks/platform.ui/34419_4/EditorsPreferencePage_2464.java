package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;

public class DialogUtil {

    private DialogUtil() {
    }

    public static void openError(Shell parent, String title, String message,
            PartInitException exception) {
        CoreException nestedException = null;
        IStatus status = exception.getStatus();
        if (status != null && status.getException() instanceof CoreException) {
			nestedException = (CoreException) status.getException();
		}
        
        IStatus errorStatus = null;

		if (nestedException != null) {
			errorStatus = StatusUtil.newStatus(nestedException.getStatus(),
					message);
		} else {
			errorStatus = new Status(IStatus.ERROR,
					WorkbenchPlugin.PI_WORKBENCH, message);
		}

		StatusUtil.handleStatus(errorStatus, StatusManager.SHOW, parent);
    }

    public static String removeAccel(String label) {

        int startBracket = label.indexOf("(&"); //$NON-NLS-1$
        if (startBracket >= 0) {
            int endBracket = label.indexOf(')');

            if ((endBracket - startBracket) == 3) {
				return label.substring(0, startBracket)
                        + label.substring(endBracket + 1);
			}
        }

        int i = label.indexOf('&');
        if (i >= 0) {
			label = label.substring(0, i) + label.substring(i + 1);
		}

        return label;
    }

    public static int availableRows(Composite parent) {

        int fontHeight = (parent.getFont().getFontData())[0].getHeight();
        int displayHeight = parent.getDisplay().getClientArea().height;

        return displayHeight / fontHeight;
    }

    public static boolean inRegularFontMode(Composite parent) {

        return availableRows(parent) > 50;
    }
}
