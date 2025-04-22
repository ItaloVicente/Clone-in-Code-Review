package org.eclipse.ui;

public interface IPageListener {
    public void pageActivated(IWorkbenchPage page);

    public void pageClosed(IWorkbenchPage page);

    public void pageOpened(IWorkbenchPage page);
}
