package org.eclipse.ui.examples.readmetool;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class WindowActionDelegate implements IWorkbenchWindowActionDelegate {
    private IWorkbenchWindow window;

    private ISelection selection;

    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
        this.window = window;
    }

    public void run(IAction action) {
        SectionsDialog dialog = new SectionsDialog(window.getShell(),
                ReadmeModelFactory.getInstance().getSections(selection));
        dialog.open();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }
}
