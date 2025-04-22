package org.eclipse.ui.internal;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.registry.PerspectiveDescriptor;

public class SavePerspectiveAction extends PerspectiveAction {

    public SavePerspectiveAction(IWorkbenchWindow window) {
        super(window);
        setText(WorkbenchMessages.SavePerspective_text);
        setActionDefinitionId(IWorkbenchCommandConstants.WINDOW_SAVE_PERSPECTIVE_AS);
        setToolTipText(WorkbenchMessages.SavePerspective_toolTip); 
        window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.SAVE_PERSPECTIVE_ACTION);
    }

    @Override
	protected void run(IWorkbenchPage page, IPerspectiveDescriptor persp) {
        PerspectiveDescriptor desc = (PerspectiveDescriptor) persp;
        if (desc != null) {
        }
    }



}
