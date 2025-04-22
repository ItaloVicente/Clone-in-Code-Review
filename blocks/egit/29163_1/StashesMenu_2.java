package org.eclipse.egit.ui.internal.actions;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;

public class StashToolbarAction extends RepositoryAction implements
		IWorkbenchWindowPulldownDelegate {

	private final MenuManager menuManager = new MenuManager();

	private final StashesMenu stashesMenu = new StashesMenu();

	public StashToolbarAction() {
		super(ActionCommands.STASH_CREATE, new StashCreateHandler());

		menuManager.add(stashesMenu);
	}

	public Menu getMenu(Control parent) {
		stashesMenu.initialize(getServiceLocator());
		return menuManager.createContextMenu(parent);
	}

	@Override
	public void dispose() {
		menuManager.dispose();
		super.dispose();
	}

}
