package org.eclipse.ui;

public interface IPartListener {

    public void partActivated(IWorkbenchPart part);

    public void partBroughtToTop(IWorkbenchPart part);

    public void partClosed(IWorkbenchPart part);

    public void partDeactivated(IWorkbenchPart part);

    public void partOpened(IWorkbenchPart part);
}
