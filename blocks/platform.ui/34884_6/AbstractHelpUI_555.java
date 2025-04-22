package org.eclipse.ui.handlers;

import java.util.Map;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.ShowViewDialog;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.views.IViewDescriptor;

public final class ShowViewHandler extends AbstractHandler {

  
    public ShowViewHandler() {
    }
    
    public ShowViewHandler(boolean makeFast) {

    }
    
	@Override
	public final Object execute(final ExecutionEvent event)
			throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		final Map parameters = event.getParameters();
		final Object value = parameters
				.get(IWorkbenchCommandConstants.VIEWS_SHOW_VIEW_PARM_ID);
		

		if (value == null) {
			openOther(window);
		} else {
            try {
                openView((String) value, window);
            } catch (PartInitException e) {
                throw new ExecutionException("Part could not be initialized", e); //$NON-NLS-1$
            }
		}

		return null;
	}

	private final void openOther(final IWorkbenchWindow window) {
		final IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return;
		}
		
		final ShowViewDialog dialog = new ShowViewDialog(window,
				WorkbenchPlugin.getDefault().getViewRegistry());
		dialog.open();
		
		if (dialog.getReturnCode() == Window.CANCEL) {
			return;
		}
		
		final IViewDescriptor[] descriptors = dialog.getSelection();
		for (int i = 0; i < descriptors.length; ++i) {
			try {
                openView(descriptors[i].getId(), window);
			} catch (PartInitException e) {
				StatusUtil.handleStatus(e.getStatus(),
						WorkbenchMessages.ShowView_errorTitle
								+ ": " + e.getMessage(), //$NON-NLS-1$
						StatusManager.SHOW);
			}
		}
	}

	private final void openView(final String viewId,
			final IWorkbenchWindow activeWorkbenchWindow)
			throws PartInitException {

		final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null) {
			return;
		}

		activePage.showView(viewId);
		
	}
}
