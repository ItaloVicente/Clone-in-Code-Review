package org.eclipse.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class WorkbenchException extends CoreException {

    private static final long serialVersionUID = 3258125864872129078L;

    public WorkbenchException(String message) {
        this(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0, message, null));
    }

    public WorkbenchException(String message, Throwable nestedException) {
        this(new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0, message,
                nestedException));
    }

    public WorkbenchException(IStatus status) {
        super(status);
    }
}
