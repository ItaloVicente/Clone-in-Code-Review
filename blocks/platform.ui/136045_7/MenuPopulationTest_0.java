
package org.eclipse.ui.tests.menus;

import org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.part.ViewPart;

public class ContextMenuTest extends MenuTestCase {

	String[] expectedMenuItemLabels = {
			"cmd1",
			"cmd2",
			"cmd3",
			"Icons All",
			"Dynamic Menu",
			"something 1",
			"something 2",
			"something 3"
	};

	String subMenuLabel = "Dynamic Menu";

	String[] expectedSubMenuItemLabels = {
			"Dynamic Item 1",
			"Dynamic Item 2"
	};

	private ContextMenuViewPart part;

	public static class ContextMenuViewPart extends ViewPart {
		public static String ID = "org.eclipse.ui.tests.internal.ContextMenuTest.ContextMenuViewPart";

		protected TreeViewer viewer;

		@Override
		public void createPartControl(Composite parent) {
			viewer = new TreeViewer(parent);
		}

		@Override
		public void setFocus() {
		}
	}

	public ContextMenuTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		part = (ContextMenuViewPart) window.getActivePage().showView(ContextMenuViewPart.ID);
	}

	@Override
	protected void doTearDown() throws Exception {
		window.getActivePage().hideView(part);
		super.doTearDown();
	}

	public void testCreateBeforeRegister() {
		TreeViewer viewer = part.viewer;
		MenuManager menuMgr = new MenuManager();

		Control control = viewer.getControl();
		Menu contextMenu = menuMgr.createContextMenu(control);
		control.setMenu(contextMenu);

		part.getSite().registerContextMenu(menuMgr, viewer);

		assertContextMenu(contextMenu);
	}

	public void testRegisterBeforeCreate() {
		TreeViewer viewer = part.viewer;
		MenuManager menuMgr = new MenuManager();

		part.getSite().registerContextMenu(menuMgr, viewer);

		Control control = viewer.getControl();
		Menu contextMenu = menuMgr.createContextMenu(control);
		control.setMenu(contextMenu);

		assertContextMenu(contextMenu);
	}

	private void assertContextMenu(Menu contextMenu) {
		contextMenu.notifyListeners(SWT.Show, new Event());
		processEvents();

		Object owner = contextMenu.getData(AbstractPartRenderer.OWNING_ME);
		assertNotNull(owner);

		MenuItem[] menuItems = contextMenu.getItems();


		assertEquals("createMenuBar: Bad count", expectedMenuItemLabels.length, menuItems.length);

		int diffIndex = checkMenuItemLabels(menuItems, expectedMenuItemLabels);
		assertTrue("createMenuBar: Index mismatch at index " + diffIndex, diffIndex == ALL_OK);

		for (MenuItem item : menuItems) {
			if (item.getText().equals(subMenuLabel)) {
				item.getMenu().notifyListeners(SWT.Show, new Event());

				MenuItem[] subMenuItems = item.getMenu().getItems();


				assertEquals("createMenuBar: Bad count", expectedSubMenuItemLabels.length, subMenuItems.length);

				diffIndex = checkMenuItemLabels(subMenuItems, expectedSubMenuItemLabels);
				assertTrue("createMenuBar: Index mismatch at index " + diffIndex, diffIndex == ALL_OK);
			}
		}

		contextMenu.notifyListeners(SWT.Hide, new Event());
		processEvents();
		contextMenu.notifyListeners(SWT.Show, new Event());
		processEvents();

		menuItems = contextMenu.getItems();

		assertEquals("createMenuBar: Bad count", expectedMenuItemLabels.length, menuItems.length);

		diffIndex = checkMenuItemLabels(menuItems, expectedMenuItemLabels);
		assertTrue("createMenuBar: Index mismatch at index " + diffIndex, diffIndex == ALL_OK);

	}

}
