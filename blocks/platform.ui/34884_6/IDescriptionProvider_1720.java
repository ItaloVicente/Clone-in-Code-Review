
package org.eclipse.ui.navigator;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;

public interface ICommonViewerWorkbenchSite extends ICommonViewerSite {

	public IWorkbenchPage getPage();

	void registerContextMenu(String menuId, MenuManager menuManager,
			ISelectionProvider selectionProvider);

	IActionBars getActionBars();

	IWorkbenchWindow getWorkbenchWindow();

	IWorkbenchPart getPart();

	IWorkbenchPartSite getSite();
}
