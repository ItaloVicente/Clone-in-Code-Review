
package org.eclipse.ui.internal;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;

public abstract class PerspectiveAction extends Action implements ActionFactory.IWorkbenchAction {
    
    private IWorkbenchWindow workbenchWindow;
    
    private PerspectiveTracker tracker;

    protected PerspectiveAction(IWorkbenchWindow window) {
        Assert.isNotNull(window);
        this.workbenchWindow = window;
        tracker = new PerspectiveTracker(window, this);
    }
    
    protected IWorkbenchWindow getWindow() {
        return workbenchWindow;
    }
    
    @Override
	public void run() {
        if (workbenchWindow == null) {
            return;
        }
        IWorkbenchPage page = workbenchWindow.getActivePage();
        if (page != null && page.getPerspective() != null) {
            run(page, page.getPerspective());
        }
    }
    
    protected abstract void run(IWorkbenchPage page, IPerspectiveDescriptor persp);

    @Override
	public void dispose() {
        if (workbenchWindow == null) {
            return;
        }
        tracker.dispose();
        workbenchWindow = null;
    }

}

