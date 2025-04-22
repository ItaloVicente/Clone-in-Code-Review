package org.eclipse.ui.tests;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.harness.util.RCPTestWorkbenchAdvisor;

public class RCPSessionApplication implements IApplication {
	
	private boolean windowlessApp = false;
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		Display display = PlatformUI.createDisplay();
		try {
			PlatformUI.createAndRunWorkbench(display, new RCPTestWorkbenchAdvisor(windowlessApp));
		} finally {
			if (display != null)
				display.dispose();
		}
		return EXIT_OK;
	}

	@Override
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
	
	public void setWindowlessApp(boolean windowlessApp) {
		this.windowlessApp = windowlessApp;
	}
	
}
