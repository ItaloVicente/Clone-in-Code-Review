package org.eclipse.ui.examples.readmetool;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class ViewActionDelegate implements IViewActionDelegate {
    public IViewPart view;

    public ViewActionDelegate() {
        super();
    }

    public void init(IViewPart view) {
        this.view = view;
    }

    public void run(org.eclipse.jface.action.IAction action) {
        MessageDialog.openInformation(view.getSite().getShell(), MessageUtil
                .getString("Readme_Editor"), //$NON-NLS-1$
                MessageUtil.getString("View_Action_executed")); //$NON-NLS-1$
    }

    public void selectionChanged(org.eclipse.jface.action.IAction action,
            org.eclipse.jface.viewers.ISelection selection) {
    }
}
