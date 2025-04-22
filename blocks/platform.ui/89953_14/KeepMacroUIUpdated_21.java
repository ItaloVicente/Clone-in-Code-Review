package org.eclipse.e4.ui.macros.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.macros.Activator;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class UserNotifications {

	public static void setMessage(String message) {
		IStatusLineManager statusLineManager = UserNotifications.getStatusLineManager();
		if (statusLineManager != null) {
			statusLineManager.setMessage(message);
			if (message == null) {
				statusLineManager.setErrorMessage(null);
			}
		}
	}

	public static void showErrorMessage(String message) {
		Activator plugin = Activator.getDefault();
		if (plugin != null) {
			plugin.getLog().log(new Status(IStatus.INFO, plugin.getBundle().getSymbolicName(), message));
		}

		IStatusLineManager statusLineManager = UserNotifications.getStatusLineManager();
		if (statusLineManager == null) {
			MessageDialog.openWarning(UserNotifications.getParent(), Messages.Activator_ErrorMacroRecording, message);
		} else {
			statusLineManager.setErrorMessage(message);
			Display current = Display.getCurrent();
			if (current != null) {
				current.beep();
			}
		}
	}

	private static Shell getParent() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return null;
		}
		return activeWorkbenchWindow.getShell();
	}

	private static IStatusLineManager getStatusLineManager() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return null;
		}
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null) {
			return null;
		}
		IEditorPart activeEditor = activePage.getActiveEditor();
		if (activeEditor == null) {
			return null;
		}
		IEditorSite editorSite = activeEditor.getEditorSite();
		if (editorSite == null) {
			return null;
		}
		return editorSite.getActionBars().getStatusLineManager();
	}

}
