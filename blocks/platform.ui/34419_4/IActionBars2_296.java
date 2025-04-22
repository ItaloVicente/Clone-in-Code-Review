package org.eclipse.ui;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.services.IServiceLocator;

public interface IActionBars {
    public void clearGlobalActionHandlers();

    public IAction getGlobalActionHandler(String actionId);

    public IMenuManager getMenuManager();
    
	public IServiceLocator getServiceLocator();

    public IStatusLineManager getStatusLineManager();

    public IToolBarManager getToolBarManager();

    public void setGlobalActionHandler(String actionId, IAction handler);

    public void updateActionBars();
}
