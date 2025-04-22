
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

		assertEquals(9, contextMenu.getItemCount());


		for (MenuItem item : contextMenu.getItems()) {
			if (item.getText().equals("Dynamic Menu")) {
				item.getMenu().notifyListeners(SWT.Show, new Event());
				assertEquals(2, item.getMenu().getItemCount());
			}
		}

		contextMenu.notifyListeners(SWT.Hide, new Event());
		processEvents();
		contextMenu.notifyListeners(SWT.Show, new Event());
		processEvents();

		assertEquals(9, contextMenu.getItemCount());

	}

}
