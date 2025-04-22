package org.eclipse.ui.handlers;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.SelectPerspectiveDialog;

public final class ShowPerspectiveHandler extends AbstractHandler {


	@Override
	public final Object execute(final ExecutionEvent event)
			throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);

		final Map parameters = event.getParameters();
		final Object value = parameters
				.get(IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE_PARM_ID);
		final String newWindow = (String) parameters
				.get(IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE_PARM_NEWWINDOW);

		if (value == null) {
			openOther(window);
		} else {

			if (newWindow == null || newWindow.equalsIgnoreCase("false")) { //$NON-NLS-1$
				openPerspective((String) value, window);
			} else {
				openNewWindowPerspective((String) value, window);
			}
		}
		return null;
	}

	private void openNewWindowPerspective(String perspectiveId,
			IWorkbenchWindow activeWorkbenchWindow) throws ExecutionException {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		try {
			IAdaptable input = ((Workbench) workbench).getDefaultPageInput();
			workbench.openWorkbenchWindow(perspectiveId, input);
		} catch (WorkbenchException e) {
			ErrorDialog.openError(activeWorkbenchWindow.getShell(),
					WorkbenchMessages.ChangeToPerspectiveMenu_errorTitle, e
							.getMessage(), e.getStatus());
		}
	}

	private final void openOther(final IWorkbenchWindow activeWorkbenchWindow)
			throws ExecutionException {
		final SelectPerspectiveDialog dialog = new SelectPerspectiveDialog(
				activeWorkbenchWindow.getShell(), WorkbenchPlugin.getDefault()
						.getPerspectiveRegistry());
		dialog.open();
		if (dialog.getReturnCode() == Window.CANCEL) {
			return;
		}

		final IPerspectiveDescriptor descriptor = dialog.getSelection();
		if (descriptor != null) {
			int openPerspMode = WorkbenchPlugin.getDefault().getPreferenceStore()
					.getInt(IPreferenceConstants.OPEN_PERSP_MODE);
			IWorkbenchPage page = activeWorkbenchWindow.getActivePage();
			IPerspectiveDescriptor persp = page == null ? null : page.getPerspective();
			String perspectiveId = descriptor.getId();
			if (IPreferenceConstants.OPM_NEW_WINDOW == openPerspMode && persp != null) {
				openNewWindowPerspective(perspectiveId, activeWorkbenchWindow);
			} else {
				openPerspective(perspectiveId, activeWorkbenchWindow);
			}
		}
	}

	private final void openPerspective(final String perspectiveId,
			final IWorkbenchWindow activeWorkbenchWindow)
			throws ExecutionException {
		final IWorkbench workbench = PlatformUI.getWorkbench();

		final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		IPerspectiveDescriptor desc = activeWorkbenchWindow.getWorkbench()
				.getPerspectiveRegistry().findPerspectiveWithId(perspectiveId);
		if (desc == null) {
			throw new ExecutionException("Perspective " + perspectiveId //$NON-NLS-1$
					+ " cannot be found."); //$NON-NLS-1$
		}

		try {
			if (activePage != null) {
				activePage.setPerspective(desc);
			} else {
				IAdaptable input = ((Workbench) workbench)
						.getDefaultPageInput();
				activeWorkbenchWindow.openPage(perspectiveId, input);
			}
		} catch (WorkbenchException e) {
			throw new ExecutionException("Perspective could not be opened.", e); //$NON-NLS-1$
		}
	}
}
