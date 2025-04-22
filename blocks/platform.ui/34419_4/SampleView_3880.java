
package org.eclipse.e4.ui.tests.workbench;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class SampleToolControl {

	public static String CONTRIBUTION_URI = "bundleclass://org.eclipse.e4.ui.tests/org.eclipse.e4.ui.tests.workbench.SampleToolControl"; //$NON-NLS-1$

	boolean shellDisposed = false;
	boolean shellEagerlyDestroyed = false;

	@PostConstruct
	void construct(MWindow window) {
		Shell shell = (Shell) window.getWidget();
		shell.addListener(SWT.Dispose, new Listener() {
			public void handleEvent(Event event) {
				shellDisposed = true;
			}
		});
	}

	@PreDestroy
	void destroy() {
		if (shellDisposed) {
			shellEagerlyDestroyed = true;
		}
	}

}
