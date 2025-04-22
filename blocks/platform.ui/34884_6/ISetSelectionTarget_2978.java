package org.eclipse.ui.part;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchSite;


public interface IPageSite extends IWorkbenchSite {
    public void registerContextMenu(String menuId, MenuManager menuManager,
            ISelectionProvider selectionProvider);

    public IActionBars getActionBars();
}
