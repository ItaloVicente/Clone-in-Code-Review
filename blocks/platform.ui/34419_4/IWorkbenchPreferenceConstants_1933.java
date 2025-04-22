package org.eclipse.ui;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.services.IServiceLocator;

public interface IWorkbenchPartSite extends IWorkbenchSite {

    public String getId();

    public String getPluginId();

    public String getRegisteredName();

    public void registerContextMenu(String menuId, MenuManager menuManager,
            ISelectionProvider selectionProvider);

    public void registerContextMenu(MenuManager menuManager,
            ISelectionProvider selectionProvider);

    @Deprecated
	public IKeyBindingService getKeyBindingService();
    
    public IWorkbenchPart getPart(); 
}
