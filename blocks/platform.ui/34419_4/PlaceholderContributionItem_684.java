
package org.eclipse.ui.internal;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PerspectiveAdapter;

public class PerspectiveTracker extends PerspectiveAdapter implements
        IPageListener {

    private IWorkbenchWindow window;

    private IAction action;

    protected PerspectiveTracker(IWorkbenchWindow window) {
        Assert.isNotNull(window);
        this.window = window;
        window.addPageListener(this);
        window.addPerspectiveListener(this);
    }

    public PerspectiveTracker(IWorkbenchWindow window, IAction action) {
        this(window);
        this.action = action;
        update();
    }

    public void dispose() {
        if (window != null) {
            window.removePageListener(this);
            window.removePerspectiveListener(this);
        }
    }

    @Override
	public void pageActivated(IWorkbenchPage page) {
        update();
    }

    @Override
	public void pageClosed(IWorkbenchPage page) {
        update();
    }

    @Override
	public void pageOpened(IWorkbenchPage page) {
    }

    @Override
	public void perspectiveActivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {
        update();
    }

    private void update() {
        if (window != null) {
            IPerspectiveDescriptor persp = null;
            IWorkbenchPage page = window.getActivePage();
            if (page != null) {
                persp = page.getPerspective();
            }
            update(persp);
        }
    }

    protected void update(IPerspectiveDescriptor persp) {
        if (action != null) {
            action.setEnabled(persp != null);
        }
    }

}
