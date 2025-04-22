package org.eclipse.ui.splash;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;

public abstract class AbstractSplashHandler {

	private Shell shell;

	public void init(Shell splash) {
		this.shell = splash;
	}

	public void dispose() {
		shell.close();
		shell = null;
	}

	public IProgressMonitor getBundleProgressMonitor() {
		return new NullProgressMonitor();
	}

	public Shell getSplash() {
		return shell;
	}
}
