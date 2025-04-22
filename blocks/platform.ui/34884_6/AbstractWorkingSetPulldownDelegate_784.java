
package org.eclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate2;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.WorkingSetRegistry;

public abstract class AbstractWorkingSetPulldownDelegate implements
		IWorkbenchWindowActionDelegate, IWorkbenchWindowPulldownDelegate2 {

	private Menu menubarMenu;

	private Menu toolbarMenu;

	private ISelection selection;

	private IWorkbenchWindow window;

	public AbstractWorkingSetPulldownDelegate() {
		super();
	}

	@Override
	public void dispose() {
		if (menubarMenu != null) {
			menubarMenu.dispose();
			menubarMenu = null;
		}
		if (toolbarMenu != null) {
			toolbarMenu.dispose();
			toolbarMenu = null;
		}
	}

	@Override
	public Menu getMenu(Control parent) {
		if (toolbarMenu != null) {
			toolbarMenu.dispose();
		}
		toolbarMenu = new Menu(parent);
		initMenu(toolbarMenu);
		return toolbarMenu;
	}

	@Override
	public Menu getMenu(Menu parent) {
		if (menubarMenu != null) {
			menubarMenu.dispose();
		}
		menubarMenu = new Menu(parent);
		initMenu(menubarMenu);
		return menubarMenu;
	}

	private void initMenu(Menu menu) {
		menu.addMenuListener(new MenuAdapter() {
			@Override
			public void menuShown(MenuEvent e) {
				Menu m = (Menu) e.widget;
				MenuItem[] items = m.getItems();
				for (int i = 0; i < items.length; i++) {
					items[i].dispose();
				}
				fillMenu(m);
			}

		});
	}

	protected abstract void fillMenu(Menu m);

	protected IWorkingSet[][] splitSets() {
		IWorkingSet[] allSets = getWindow().getWorkbench().getWorkingSetManager()
				.getWorkingSets();

		Map map = new HashMap();
		WorkingSetRegistry registry = WorkbenchPlugin.getDefault()
				.getWorkingSetRegistry();

		for (int i = 0; i < allSets.length; i++) {
			String setType = allSets[i].getId();
			if (WorkbenchActivityHelper.filterItem(registry
					.getWorkingSetDescriptor(setType))) {
				continue;
			}
			List setsOfType = (List) map.get(setType);
			if (setsOfType == null) {
				setsOfType = new ArrayList();
				map.put(setType, setsOfType);
			}
			setsOfType.add(allSets[i]);
		}

		IWorkingSet[][] typedSets = new IWorkingSet[map.keySet().size()][];
		int i = 0;
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			List setsOfType = (List) map.get(iter.next());
			typedSets[i] = new IWorkingSet[setsOfType.size()];
			setsOfType.toArray(typedSets[i++]);
		}
		return typedSets;
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	protected IWorkbenchWindow getWindow() {
		return window;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	protected ISelection getSelection() {
		return selection;
	}
}
