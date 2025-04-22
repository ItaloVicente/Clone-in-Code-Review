package org.eclipse.ui.tests.menus;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.ShowViewMenu;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class ShowViewMenuTest extends UITestCase {

	private IWorkbenchWindow workbenchWindow;

	public ShowViewMenuTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		workbenchWindow = openTestWindow();
	}
	

	public void testMenuOnlyHasShowViewAction() {
		Menu swtMenu = new Menu(workbenchWindow.getShell());
		ShowViewMenu showViewMenu = new ShowViewMenu(workbenchWindow, "id"); //$NON-NLS-1$
		showViewMenu.fill(swtMenu, 0);

		assertEquals("Only the 'Other...' action should be available", 1, swtMenu.getItemCount());
	}

	public void testFastViewMenuVariantOnlyHasShowViewAction() {
		Menu swtMenu = new Menu(workbenchWindow.getShell());
		ShowViewMenu showViewMenu = new ShowViewMenu(workbenchWindow,
				"id", true); //$NON-NLS-1$
		showViewMenu.fill(swtMenu, 0);

		assertEquals("Only the 'Other...' action should be available", 1, swtMenu.getItemCount());
	}
	

}
