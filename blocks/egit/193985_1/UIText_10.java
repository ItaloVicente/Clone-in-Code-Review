
package org.eclipse.egit.ui.internal;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egit.core.Activator;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class ToolsUtils {

	public static int askUserAboutToolExecution(String textHeader,
			String message) {
		AtomicInteger result = new AtomicInteger();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				boolean ok = MessageDialog.openQuestion(null, textHeader,
						message);
				result.set(ok ? SWT.YES : SWT.NO);
			}
		};
		if (Display.getCurrent() == null) {
			PlatformUI.getWorkbench().getDisplay().syncExec(runnable);
		} else {
			runnable.run();
		}
		return result.get();
	}

	public static void informUserAboutError(String textHeader, String message) {
		IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
				message);
		Runnable runnable = () -> ErrorDialog.openError(null, textHeader,
				null, status);
		if (Display.getCurrent() == null) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(runnable);
		} else {
			runnable.run();
		}
	}

	public static void informUser(String textHeader, String message) {
		Runnable runnable = () -> MessageDialog.openInformation(null,
				textHeader, message);
		if (Display.getCurrent() == null) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(runnable);
		} else {
			runnable.run();
		}
	}
}
