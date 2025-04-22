package org.eclipse.egit.ui.internal.components;

import java.util.Collection;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

public abstract class ToolbarMenuAction extends Action
		implements IWorkbenchAction, IMenuCreator {

	private Menu menu;

	private boolean showMenu;

	public ToolbarMenuAction(String title) {
		super(title, IAction.AS_DROP_DOWN_MENU);
	}

	@Override
	public void run() {
		showMenu = true;
	}

	@Override
	public void runWithEvent(Event event) {
		if (!isEnabled()) {
			return;
		}
		showMenu = false;
		run();
		Widget widget = event.widget;
		if (showMenu && (widget instanceof ToolItem)) {
			ToolItem item = (ToolItem) widget;
			Rectangle bounds = item.getBounds();
			event.detail = SWT.ARROW;
			event.x = bounds.x;
			event.y = bounds.y + bounds.height;
			item.notifyListeners(SWT.Selection, event);
		}
	}

	@Override
	public IMenuCreator getMenuCreator() {
		return this;
	}

	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}

	@Override
	public Menu getMenu(Control parent) {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
		if (isEnabled()) {
			menu = new Menu(parent);
			for (IAction action : getActions()) {
				ActionContributionItem item = new ActionContributionItem(
						action);
				item.fill(menu, -1);
			}
		}
		return menu;
	}

	@NonNull
	protected abstract Collection<IAction> getActions();

	@Override
	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
	}

}
