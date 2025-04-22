package org.eclipse.ui.actions;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.jface.action.Action;

import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;

public class OpenInNewWindowAction extends Action implements
        ActionFactory.IWorkbenchAction {

    private IWorkbenchWindow workbenchWindow;

    private IAdaptable pageInput;

    public OpenInNewWindowAction(IWorkbenchWindow window) {
        this(window, ((Workbench) window.getWorkbench()).getDefaultPageInput());
		setActionDefinitionId(IWorkbenchCommandConstants.WINDOW_NEW_WINDOW);
    }

    public OpenInNewWindowAction(IWorkbenchWindow window, IAdaptable input) {
        super(WorkbenchMessages.OpenInNewWindowAction_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        setToolTipText(WorkbenchMessages.OpenInNewWindowAction_toolTip);
        pageInput = input;
        window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.OPEN_NEW_WINDOW_ACTION);
    }

    public void setPageInput(IAdaptable input) {
        pageInput = input;
    }

    @Override
	public void run() {
        if (workbenchWindow == null) {
            return;
        }
        try {
            String perspId;

            IWorkbenchPage page = workbenchWindow.getActivePage();
            if (page != null && page.getPerspective() != null) {
				perspId = page.getPerspective().getId();
			} else {
				perspId = workbenchWindow.getWorkbench()
                        .getPerspectiveRegistry().getDefaultPerspective();
			}

            workbenchWindow.getWorkbench().openWorkbenchWindow(perspId,
                    pageInput);
        } catch (WorkbenchException e) {
			StatusUtil.handleStatus(e.getStatus(),
					WorkbenchMessages.OpenInNewWindowAction_errorTitle
							+ ": " + e.getMessage(), //$NON-NLS-1$
					StatusManager.SHOW);
        }
    }

    @Override
	public void dispose() {
        workbenchWindow = null;
    }
}
