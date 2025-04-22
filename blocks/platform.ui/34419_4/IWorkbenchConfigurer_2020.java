package org.eclipse.ui.application;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;

public interface IActionBarConfigurer {
    
    public IWorkbenchWindowConfigurer getWindowConfigurer();
    
    
    public IMenuManager getMenuManager();

    public IStatusLineManager getStatusLineManager();

    public ICoolBarManager getCoolBarManager();
    
    public void registerGlobalAction(IAction action);

}
