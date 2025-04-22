
package org.eclipse.ui.statushandlers;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.statushandlers.IStatusDialogConstants;
import org.eclipse.ui.statushandlers.StatusManager.INotificationTypes;

public class WorkbenchErrorHandler extends AbstractStatusHandler {

	@Override
	public boolean supportsNotification(int type) {
		if (type == INotificationTypes.HANDLED) {
			return true;
		}
		return super.supportsNotification(type);
	}

	private WorkbenchStatusDialogManager statusDialogManager;

	@Override
	public void handle(final StatusAdapter statusAdapter, int style) {
		statusAdapter.setProperty(WorkbenchStatusDialogManager.HINT,
				new Integer(style));
		if (((style & StatusManager.SHOW) == StatusManager.SHOW)
				|| ((style & StatusManager.BLOCK) == StatusManager.BLOCK)) {

			final boolean block = ((style & StatusManager.BLOCK) == StatusManager.BLOCK);
			
			if (Display.getCurrent() != null) {
				showStatusAdapter(statusAdapter, block);
			} else {
				if (block) {
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							showStatusAdapter(statusAdapter, true);
						}
					});

				} else {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							showStatusAdapter(statusAdapter, false);
						}
					});
				}
			}
		}

		if ((style & StatusManager.LOG) == StatusManager.LOG) {
			StatusManager.getManager().addLoggedStatus(
					statusAdapter.getStatus());
			WorkbenchPlugin.getDefault().getLog()
					.log(statusAdapter.getStatus());
		}
	}
	
	private void showStatusAdapter(StatusAdapter statusAdapter, boolean block) {
		if (!PlatformUI.isWorkbenchRunning()) {
			WorkbenchPlugin.log(statusAdapter.getStatus());
			return;
		}

		getStatusDialogManager().addStatusAdapter(statusAdapter, block);

		if (block) {
			Shell shell;
			while ((shell = getStatusDialogShell()) != null
					&& !shell.isDisposed()) {
				if (!shell.getDisplay().readAndDispatch()) {
					Display.getDefault().sleep();
				}
			}
		}
	}
	
	private Shell getStatusDialogShell() {
		return (Shell) getStatusDialogManager().getProperty(
				IStatusDialogConstants.SHELL);
	}

	private WorkbenchStatusDialogManager getStatusDialogManager() {
		if (statusDialogManager == null) {
			synchronized (this) {
				if (statusDialogManager == null) {
					statusDialogManager = new WorkbenchStatusDialogManager(null);
					statusDialogManager.setProperty(
							IStatusDialogConstants.SHOW_SUPPORT, Boolean.TRUE);
					statusDialogManager.setProperty(
							IStatusDialogConstants.HANDLE_OK_STATUSES,
							Boolean.TRUE);
					statusDialogManager.setProperty(
							IStatusDialogConstants.ERRORLOG_LINK, Boolean.TRUE);
					configureStatusDialog(statusDialogManager);
				}
			}
		}
		return statusDialogManager;
	}

	protected void configureStatusDialog(
			final WorkbenchStatusDialogManager statusDialog) {
	}
}
