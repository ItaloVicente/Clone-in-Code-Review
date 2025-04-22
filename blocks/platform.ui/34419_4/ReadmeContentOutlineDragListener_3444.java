package org.eclipse.ui.examples.readmetool;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class PopupMenuActionDelegate implements IObjectActionDelegate {

    private IWorkbenchPart part;

    @Override
	public void run(IAction action) {
        MessageDialog.openInformation(this.part.getSite().getShell(),
                MessageUtil.getString("Readme_Example"), //$NON-NLS-1$
                MessageUtil.getString("Popup_Menu_Action_executed")); //$NON-NLS-1$
    }

    @Override
	public void selectionChanged(IAction action, ISelection selection) {
    }

    @Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        this.part = targetPart;
    }
}
