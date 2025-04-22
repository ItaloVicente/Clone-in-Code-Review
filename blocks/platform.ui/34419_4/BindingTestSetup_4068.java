
package org.eclipse.jface.tests.databinding;

import org.eclipse.swt.widgets.Shell;

public abstract class AbstractSWTTestCase extends AbstractDefaultRealmTestCase {
	private Shell shell;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		if (shell != null && !shell.isDisposed()) {
			shell.dispose();
		}
		super.tearDown();
	}

	protected final Shell getShell() {
		if (shell == null || shell.isDisposed()) {
			shell = createShell();
		}

		return shell;
	}

	protected Shell createShell() {
		return new Shell();
	}
}
