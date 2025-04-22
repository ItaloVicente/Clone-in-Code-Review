
package org.eclipse.ui.internal;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;

class ApplicationMenuManager extends MenuManager {

	private final Menu appMenu;
	private boolean disposing;

	public ApplicationMenuManager(Menu appMenu) {
		this.appMenu = appMenu;
	}

	@Override
	public Menu createMenuBar(Decorations parent) {
		return appMenu;
	}

	@Override
	protected boolean menuExist() {
		return !disposing;
	}

	@Override
	protected int getMenuItemCount() {
		return appMenu.getItemCount();
	}

	@Override
	protected Item getMenuItem(int index) {
		return appMenu.getItem(index);
	}

	@Override
	protected Item[] getMenuItems() {
		return appMenu.getItems();
	}

	@Override
	protected void doItemFill(IContributionItem ci, int index) {
		ci.fill(appMenu, index);
	}

	@Override
	public void dispose() {
		disposing = true;
		super.dispose();
		disposing = false;
	}
}
