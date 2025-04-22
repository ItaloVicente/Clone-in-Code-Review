
package org.eclipse.ui.actions;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.statushandlers.StatusManager;

@Deprecated
public class OpenPerspectiveMenu extends PerspectiveMenu {
    private IAdaptable pageInput;

    private IMenuManager parentMenuManager;

    private boolean replaceEnabled = true;

    private static String PAGE_PROBLEMS_TITLE = WorkbenchMessages.OpenPerspectiveMenu_pageProblemsTitle; 

    private static String PAGE_PROBLEMS_MESSAGE = WorkbenchMessages.OpenPerspectiveMenu_errorUnknownInput;

    public OpenPerspectiveMenu(IMenuManager menuManager, IWorkbenchWindow window) {
        this(window);
        this.parentMenuManager = menuManager;
    }

    public OpenPerspectiveMenu(IWorkbenchWindow window) {
        this(window, null);
        showActive(true);
    }

    public OpenPerspectiveMenu(IWorkbenchWindow window, IAdaptable input) {
        super(window, "Open New Page Menu");//$NON-NLS-1$
        this.pageInput = input;
    }

    private boolean canRun() {
        if (openPerspectiveSetting().equals(
                IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_REPLACE)) {
			return replaceEnabled;
		}
        return true;
    }

    private String openPerspectiveSetting() {
        return PrefUtil.getAPIPreferenceStore().getString(
                IWorkbenchPreferenceConstants.OPEN_NEW_PERSPECTIVE);
    }

    @Override
	protected void run(IPerspectiveDescriptor desc) {
        openPage(desc, 0);
    }

    @Override
	protected void run(IPerspectiveDescriptor desc, SelectionEvent event) {
        openPage(desc, event.stateMask);
    }

    private void openPage(IPerspectiveDescriptor desc, int keyStateMask) {
        if (pageInput == null) {			
			StatusUtil.handleStatus(PAGE_PROBLEMS_TITLE
					+ ": " + PAGE_PROBLEMS_MESSAGE, StatusManager.SHOW); //$NON-NLS-1$
			return;
        }

        try {
            getWindow().getWorkbench().showPerspective(desc.getId(),
                    getWindow(), pageInput);
        } catch (WorkbenchException e) {
			StatusUtil.handleStatus(
					PAGE_PROBLEMS_TITLE + ": " + e.getMessage(), e,  //$NON-NLS-1$
					StatusManager.SHOW);
		}
    }

    public void setPageInput(IAdaptable input) {
        pageInput = input;
    }

    public void setReplaceEnabled(boolean isEnabled) {
        if (replaceEnabled != isEnabled) {
            replaceEnabled = isEnabled;
            if (canRun() && parentMenuManager != null) {
				parentMenuManager.update(true);
			}
        }
    }
}
