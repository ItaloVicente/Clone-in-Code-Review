package org.eclipse.ui.internal;

import org.eclipse.jface.window.Window;

public final class ExceptionHandler implements Window.IExceptionHandler {

    private static final ExceptionHandler instance = new ExceptionHandler();

    public static ExceptionHandler getInstance() {
        return instance;
    }

    private int exceptionCount = 0; // To avoid recursive errors

    private ExceptionHandler() {
    }

    @Override
	public void handleException(Throwable t) {
        try {
            if (t instanceof ThreadDeath) {
                throw (ThreadDeath) t;
            }

            exceptionCount++;
            if (exceptionCount > 2) {
                if (t instanceof RuntimeException) {
					throw (RuntimeException) t;
				} else if (t instanceof Error) {
					throw (Error) t;
				} else {
					throw new RuntimeException(t);
				}
            }

            Workbench wb = Workbench.getInstance();
            if (wb != null) {
                wb.getAdvisor().eventLoopException(t);
            }
        } finally {
            exceptionCount--;
        }
    }
}
