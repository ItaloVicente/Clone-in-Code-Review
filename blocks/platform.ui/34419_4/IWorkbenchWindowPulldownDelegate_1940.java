package org.eclipse.ui;

public interface IWorkbenchWindowActionDelegate extends IActionDelegate {
    public void dispose();

    public void init(IWorkbenchWindow window);
}
