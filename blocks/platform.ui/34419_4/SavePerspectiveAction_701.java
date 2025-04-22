package org.eclipse.ui.internal;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ResetPerspectiveAction extends PerspectiveAction {

    public ResetPerspectiveAction() {
        this(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
    }

    public ResetPerspectiveAction(IWorkbenchWindow window) {
        super(window);
        setText(WorkbenchMessages.ResetPerspective_text);
        setActionDefinitionId(IWorkbenchCommandConstants.WINDOW_RESET_PERSPECTIVE);
        setToolTipText(WorkbenchMessages.ResetPerspective_toolTip); 
        window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.RESET_PERSPECTIVE_ACTION);
    }

    @Override
	protected void run(IWorkbenchPage page, IPerspectiveDescriptor persp) {
        String message = NLS.bind(WorkbenchMessages.ResetPerspective_message, persp.getLabel() );
        String[] buttons = new String[] { IDialogConstants.OK_LABEL,
                IDialogConstants.CANCEL_LABEL };
        MessageDialog d = new MessageDialog(getWindow().getShell(),
                WorkbenchMessages.ResetPerspective_title,
                null, message, MessageDialog.QUESTION, buttons, 0);
        if (d.open() == 0) {
			page.resetPerspective();
		}
    }

}
