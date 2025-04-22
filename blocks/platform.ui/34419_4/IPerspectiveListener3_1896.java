package org.eclipse.ui;

public interface IPerspectiveListener2 extends IPerspectiveListener {

    public void perspectiveChanged(IWorkbenchPage page,
            IPerspectiveDescriptor perspective,
            IWorkbenchPartReference partRef, String changeId);
}
