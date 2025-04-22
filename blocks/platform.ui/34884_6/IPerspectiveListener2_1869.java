package org.eclipse.ui;

public interface IPerspectiveListener {
    public void perspectiveActivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective);

    public void perspectiveChanged(IWorkbenchPage page,
            IPerspectiveDescriptor perspective, String changeId);
}
