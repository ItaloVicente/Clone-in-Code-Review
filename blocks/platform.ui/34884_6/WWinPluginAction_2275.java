package org.eclipse.ui.internal;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars2;
import org.eclipse.ui.services.IServiceLocator;

public class WWinActionBars implements IActionBars2 {
    private WorkbenchWindow window;

    public WWinActionBars(WorkbenchWindow window) {
        super();
        this.window = window;
    }

    @Override
	public void clearGlobalActionHandlers() {
    }

    @Override
	public ICoolBarManager getCoolBarManager() {
        return window.getCoolBarManager2();
    }

    @Override
	public IAction getGlobalActionHandler(String actionID) {
        return null;
    }

    @Override
	public IMenuManager getMenuManager() {
        return window.getMenuManager();
    }

	@Override
	public final IServiceLocator getServiceLocator() {
		return window;
	}

    @Override
	public IStatusLineManager getStatusLineManager() {
        return window.getStatusLineManager();
    }

    @Override
	public IToolBarManager getToolBarManager() {
        Assert.isTrue(false);
        return null;
    }

    @Override
	public void setGlobalActionHandler(String actionID, IAction handler) {
    }

    @Override
	public void updateActionBars() {
        window.updateActionBars();
    }
}
