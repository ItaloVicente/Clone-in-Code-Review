package org.eclipse.ui.internal;

import com.ibm.icu.text.MessageFormat;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.internal.misc.UIStats;

public class EarlyStartupRunnable extends SafeRunnable {

    private IExtension extension;

    public EarlyStartupRunnable(IExtension extension) {
        this.extension = extension;
    }

    @Override
	public void run() throws Exception {
		IConfigurationElement[] configElements = extension.getConfigurationElements();

        for (IConfigurationElement element : configElements) {
            if (element != null&& element.getName().equals(IWorkbenchConstants.TAG_STARTUP)) {
                runEarlyStartup(getExecutableExtension(element));
            }
        }
    }

    @Override
	public void handleException(Throwable exception) {
		IStatus status = new Status(IStatus.ERROR, extension.getNamespaceIdentifier(), 0,
                "Unable to execute early startup code for an extension", //$NON-NLS-1$
                exception);
		WorkbenchPlugin.log(status);
    }

    private void runEarlyStartup(Object executableExtension) {
		if (executableExtension instanceof IStartup) {
			String methodName = executableExtension.getClass().getName() + ".earlyStartup"; //$NON-NLS-1$
			try {
				UIStats.start(UIStats.EARLY_STARTUP, methodName);
				((IStartup) executableExtension).earlyStartup();
			} finally {
				UIStats.end(UIStats.EARLY_STARTUP, executableExtension, methodName);
			}
		} else {
			String message = executableExtension == null ?
					"The startup extension does not provide a valid class attribute." : //$NON-NLS-1$
					MessageFormat.format("Startup class {0} must implement org.eclipse.ui.IStartup", //$NON-NLS-1$
							executableExtension.getClass().getName());
			IStatus status =
					new Status(IStatus.ERROR, extension.getNamespaceIdentifier(), 0, message, null);
			WorkbenchPlugin.log(status);
        }
    }

    private Object getExecutableExtension(IConfigurationElement element)
            throws CoreException {
		return WorkbenchPlugin.createExtension(element, IWorkbenchConstants.TAG_CLASS);
    }
}
