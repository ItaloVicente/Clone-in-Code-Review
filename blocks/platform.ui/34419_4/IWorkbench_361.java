package org.eclipse.ui;

public interface IWindowListener {
    public void windowActivated(IWorkbenchWindow window);

    public void windowDeactivated(IWorkbenchWindow window);

    public void windowClosed(IWorkbenchWindow window);

    public void windowOpened(IWorkbenchWindow window);

}
