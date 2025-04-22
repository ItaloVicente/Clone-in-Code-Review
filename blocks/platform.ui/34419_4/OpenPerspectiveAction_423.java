package org.eclipse.ui.actions;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;

@Deprecated
public class OpenNewWindowMenu extends PerspectiveMenu {
    private IAdaptable pageInput;

    public OpenNewWindowMenu(IWorkbenchWindow window) {
        this(window, null);
    }

    public OpenNewWindowMenu(IWorkbenchWindow window, IAdaptable input) {
        super(window, "Open New Page Menu");//$NON-NLS-1$
        this.pageInput = input;
    }

    @Override
	protected void run(IPerspectiveDescriptor desc) {
        if (pageInput == null) {
        	StatusUtil.handleStatus(
					WorkbenchMessages.OpenNewWindowMenu_dialogTitle + ": " + //$NON-NLS-1$
							WorkbenchMessages.OpenNewWindowMenu_unknownInput,
					StatusManager.SHOW);
			return;
        }

        try {
            getWindow().getWorkbench().openWorkbenchWindow(desc.getId(),
                    pageInput);
        } catch (WorkbenchException e) {
			StatusUtil.handleStatus(
					WorkbenchMessages.OpenNewWindowMenu_dialogTitle + ": " + //$NON-NLS-1$
							e.getMessage(), e, StatusManager.SHOW);
		}
    }

    public void setPageInput(IAdaptable input) {
        pageInput = input;
    }
}
