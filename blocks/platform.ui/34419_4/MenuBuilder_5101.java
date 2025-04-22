
package org.eclipse.ui.tests.menus;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.tests.menus.DeclaredProgrammaticFactory.MyItem;

public class MenuBaseTests extends MenuTestCase {
	String[] expectedIds = {
			"MenuTest.BasicCmdItem",
			"MenuTest.BasicMenu",
			"MenuTest.BeforeSeparator",
			"MenuTest.Separator",
			"MenuTest.AfterSeparator",
			"MenuTest.ParameterItem",
			null, // "MenuTest.DynamicItem",
			"MenuTest.DynamicMenu",
			"MenuTest.ItemX1",
			MenuPopulationTest.ID_DEFAULT,
			MenuPopulationTest.ID_ALL,
			MenuPopulationTest.ID_TOOLBAR,
			"myitem"
		};
		Class[] expectedClasses = {
			org.eclipse.ui.menus.CommandContributionItem.class,
			org.eclipse.jface.action.MenuManager.class,
			org.eclipse.ui.menus.CommandContributionItem.class,
			org.eclipse.jface.action.Separator.class,
			org.eclipse.ui.menus.CommandContributionItem.class,
			org.eclipse.ui.menus.CommandContributionItem.class,
			org.eclipse.ui.internal.menus.DynamicMenuContributionItem.class,
			org.eclipse.jface.action.MenuManager.class,
			org.eclipse.ui.menus.CommandContributionItem.class,
			org.eclipse.ui.menus.CommandContributionItem.class,
			org.eclipse.ui.menus.CommandContributionItem.class,
			org.eclipse.ui.menus.CommandContributionItem.class,
			MyItem.class
		};
		String[] expectedMenuItemLabels = {
			"&Basic Cmd",
			"Basic Menu",
			"Inserted &Before",
			"",
			"Inserted &After",
			"Parameter &Cmd",
			"Dynamic Item 1",
			"Dynamic Item 2",
			"Dynamic Menu",
			"Icons Default",
			"Icons All",
			"Icons Toolbar Only",
			"MyItem"
		};

	public MenuBaseTests(String testName) {
		super(testName);
	}

	public void testBasicPopulation() throws Exception {
		MenuManager manager = new MenuManager(null, TEST_CONTRIBUTIONS_CACHE_ID);
		menuService.populateContributionManager(manager, "menu:"
				+ TEST_CONTRIBUTIONS_CACHE_ID);
		IContributionItem[] items = manager.getItems();

		assertEquals("Bad count", expectedIds.length, items.length);

		int diffIndex = checkContribIds(items, expectedIds);
		assertTrue("Id mismatch at index " + diffIndex , diffIndex == ALL_OK);

		diffIndex = checkContribClasses(items, expectedClasses);
		assertTrue("Class mismatch at index " + diffIndex , diffIndex == ALL_OK);

		menuService.releaseContributions(manager);
		manager.dispose();
	}

	public void testBasicMenuPopulation() throws Exception {
		MenuManager manager = new MenuManager("Test Menu", TEST_CONTRIBUTIONS_CACHE_ID);
		menuService.populateContributionManager(manager, "menu:"
				+ TEST_CONTRIBUTIONS_CACHE_ID);

		Shell shell = window.getShell();

		final Menu menuBar = manager.createContextMenu(shell);
		Event e = new Event();
		e.type = SWT.Show;
		e.widget = menuBar;
		menuBar.notifyListeners(SWT.Show, e);

		MenuItem[] menuItems = menuBar.getItems();

		IContributionItem[] items = manager.getItems();
		printIds(items);
		printClasses(items);
		printMenuItemLabels(menuItems);

		assertEquals("createMenuBar: Bad count", expectedMenuItemLabels.length, menuItems.length);

		int diffIndex = checkMenuItemLabels(menuItems, expectedMenuItemLabels);
		assertTrue("createMenuBar: Index mismatch at index " + diffIndex , diffIndex == ALL_OK);



		menuService.releaseContributions(manager);
		manager.dispose();
	}
}
