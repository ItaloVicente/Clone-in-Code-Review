package org.eclipse.ui.actions;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;

@Deprecated
public class OpenNewPageMenu extends PerspectiveMenu {
    private IAdaptable pageInput;

    public OpenNewPageMenu(IWorkbenchWindow window) {
        this(window, null);
    }

    public OpenNewPageMenu(IWorkbenchWindow window, IAdaptable input) {
        super(window, "Open New Page Menu");//$NON-NLS-1$
        this.pageInput = input;
    }

    @Override
	protected void run(IPerspectiveDescriptor desc) {
        if (pageInput == null) {	
			StatusUtil.handleStatus(
					WorkbenchMessages.OpenNewPageMenu_dialogTitle + ": " + //$NON-NLS-1$
							WorkbenchMessages.OpenNewPageMenu_unknownPageInput,
					StatusManager.SHOW);
			return;
        }

        try {
            getWindow().openPage(desc.getId(), pageInput);
        } catch (WorkbenchException e) {
        	StatusUtil.handleStatus(
					WorkbenchMessages.OpenNewPageMenu_dialogTitle + ": " + //$NON-NLS-1$
							e.getMessage(), e, StatusManager.SHOW);
        }
    }

    public void setPageInput(IAdaptable input) {
        pageInput = input;
    }
}
