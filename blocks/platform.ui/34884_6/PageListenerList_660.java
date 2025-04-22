package org.eclipse.ui.internal;

import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.PartEventAction;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

public abstract class PageEventAction extends PartEventAction implements
        IPageListener, ActionFactory.IWorkbenchAction {
    private IWorkbenchPage activePage;

    private IWorkbenchWindow workbenchWindow;

    protected PageEventAction(String text, IWorkbenchWindow window) {
        super(text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        this.activePage = window.getActivePage();
        this.workbenchWindow.addPageListener(this);
        this.workbenchWindow.getPartService().addPartListener(this);
    }

    public final IWorkbenchPage getActivePage() {
        return activePage;
    }

    public final IWorkbenchWindow getWorkbenchWindow() {
        return workbenchWindow;
    }

    @Override
	public void pageActivated(IWorkbenchPage page) {
        this.activePage = page;
    }

    @Override
	public void pageClosed(IWorkbenchPage page) {
        if (page == activePage) {
            activePage = null;
        }
    }

    @Override
	public void pageOpened(IWorkbenchPage page) {
    }

    @Override
	public void dispose() {
        if (workbenchWindow == null) {
            return;
        }
        workbenchWindow.removePageListener(this);
        workbenchWindow.getPartService().removePartListener(this);
        workbenchWindow = null;
    }
}
