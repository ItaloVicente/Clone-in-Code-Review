package org.eclipse.ui;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;

public interface IEditorSite extends IWorkbenchPartSite {

    public IEditorActionBarContributor getActionBarContributor();

    public IActionBars getActionBars();

    public void registerContextMenu(MenuManager menuManager,
            ISelectionProvider selectionProvider, boolean includeEditorInput);

    public void registerContextMenu(String menuId, MenuManager menuManager,
            ISelectionProvider selectionProvider, boolean includeEditorInput);
}
