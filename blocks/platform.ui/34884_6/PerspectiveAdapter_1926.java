package org.eclipse.ui;

import org.eclipse.core.runtime.IStatus;

public class PartInitException extends WorkbenchException {
    
    private static final long serialVersionUID = 3257284721296684850L;

    public PartInitException(String message) {
        super(message);
    }

    public PartInitException(String message, Throwable nestedException) {
        super(message, nestedException);
    }

    public PartInitException(IStatus status) {
        super(status);
    }
}
