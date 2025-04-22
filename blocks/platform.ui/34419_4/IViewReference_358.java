package org.eclipse.ui;


public interface IViewPart extends IWorkbenchPart, IPersistable {
    public IViewSite getViewSite();

    public void init(IViewSite site) throws PartInitException;

    public void init(IViewSite site, IMemento memento) throws PartInitException;

    @Override
	public void saveState(IMemento memento);
}
