package org.eclipse.ui.tests.menus;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

public class DynamicToolbarTest extends MenuTestCase {

	public DynamicToolbarTest(String testName) {
		super(testName);
	}

	public void testDynamicMenu() throws Exception {
		ToolBarManager manager = new ToolBarManager();
		try {
			menuService.populateContributionManager(manager, "toolbar:org.eclipse.ui.trim.status");
			IContributionItem[] contributionItems = manager.getItems();
			assertEquals(1, contributionItems.length);
			assertEquals(contributionItems[0].getId(), "org.eclipse.ui.tests.dynamicToolbarContribution");

		} finally {
			menuService.releaseContributions(manager);
		}

	}

	public class MyStatusBar extends WorkbenchWindowControlContribution {

		protected Composite fTopControl = null;
		private ToolBar toolBar;
		private ToolItem toolItem;

		@Override
		protected Control createControl(Composite parent) {
			GridLayout layout = new GridLayout(1, false);
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			fTopControl = new Composite(parent, SWT.NONE);
			fTopControl.setLayout(layout);
			toolBar = new ToolBar(fTopControl, SWT.HORIZONTAL);
			toolItem = new ToolItem(toolBar, SWT.PUSH);
			toolItem.setText("StatusItem");
			toolBar.pack();
			return fTopControl;
		}
	}
}
