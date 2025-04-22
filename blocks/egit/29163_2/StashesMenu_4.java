package org.eclipse.egit.ui.internal.actions;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;

public class StashToolbarAction extends RepositoryAction implements
		IWorkbenchWindowPulldownDelegate {

	private Menu menu;
	private final StashesMenu stashesMenu = new StashesMenu();

	public StashToolbarAction() {
		super(ActionCommands.STASH_CREATE, new StashCreateHandler() {
			@Override
			public boolean isEnabled() {
				return getRepository() != null;
			}
		});
	}

	public Menu getMenu(Control parent) {
		stashesMenu.initialize(getServiceLocator());
		if (menu != null)
			menu.dispose();
		menu = new Menu(parent);
		stashesMenu.fill(menu, 0);
		return menu;
	}

	@Override
	public void dispose() {
		if (menu != null)
			menu.dispose();
		super.dispose();
	}

}
