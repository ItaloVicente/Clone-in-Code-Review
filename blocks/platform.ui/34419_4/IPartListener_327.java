package org.eclipse.ui;

public interface IPageService {
    public void addPageListener(IPageListener listener);

    public void addPerspectiveListener(IPerspectiveListener listener);

    public IWorkbenchPage getActivePage();

    public void removePageListener(IPageListener listener);

    public void removePerspectiveListener(IPerspectiveListener listener);
}
