
package org.eclipse.e4.demo.contacts.handlers;

import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ThemeUtil {

	public static void applyDialogStyles(IStylingEngine engine, Control control) {
		if (engine != null) {
			Shell shell = control.getShell();
			if (shell.getBackgroundMode() == SWT.INHERIT_NONE) {
				shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
			}

			engine.style(shell);
		}
	}
}
