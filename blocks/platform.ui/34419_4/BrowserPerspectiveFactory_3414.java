package org.eclipse.ui.examples.rcp.browser;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class BrowserApp implements IApplication {
	
	public BrowserApp() {
	}

	public Object start(IApplicationContext context) throws Exception {
		Display display = PlatformUI.createDisplay();
		try {
			int code = PlatformUI.createAndRunWorkbench(display,
					new BrowserAdvisor());
			return code == PlatformUI.RETURN_RESTART
					? EXIT_RESTART
					: EXIT_OK;
		} finally {
			if (display != null)
				display.dispose();
		}
	}

	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
