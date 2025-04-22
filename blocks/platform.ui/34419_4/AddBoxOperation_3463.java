package org.eclipse.ui.examples.readmetool;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class WindowActionDelegate implements IWorkbenchWindowActionDelegate {
    private IWorkbenchWindow window;

    private ISelection selection;

    @Override
	public void dispose() {
    }

    @Override
	public void init(IWorkbenchWindow window) {
        this.window = window;
    }

    @Override
	public void run(IAction action) {
        SectionsDialog dialog = new SectionsDialog(window.getShell(),
                ReadmeModelFactory.getInstance().getSections(selection));
        dialog.open();
    }

    @Override
	public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }
}
