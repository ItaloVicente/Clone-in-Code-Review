package org.eclipse.ui;

public interface IPerspectiveListener3 extends IPerspectiveListener2 {

    public void perspectiveOpened(IWorkbenchPage page,
            IPerspectiveDescriptor perspective);

    public void perspectiveClosed(IWorkbenchPage page,
            IPerspectiveDescriptor perspective);

    public void perspectiveDeactivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective);

    public void perspectiveSavedAs(IWorkbenchPage page,
            IPerspectiveDescriptor oldPerspective,
            IPerspectiveDescriptor newPerspective);
}
