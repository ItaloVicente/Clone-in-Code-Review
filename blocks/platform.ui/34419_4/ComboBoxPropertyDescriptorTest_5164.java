
package org.eclipse.ui.tests.propertysheet;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.eclipse.ui.views.properties.PropertySheet;

public abstract class AbstractPropertySheetTest extends UITestCase {

	private static final String PIN_PROPERTY_SHEET_ACTION_ID_PREFIX = "org.eclipse.ui.views.properties.PinPropertySheetAction";
	protected IWorkbenchPage activePage;
	protected PropertySheet propertySheet;

	public AbstractPropertySheetTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		IWorkbenchWindow workbenchWindow = openTestWindow();
		activePage = workbenchWindow.getActivePage();
	}

	@Override
	protected void doTearDown() throws Exception {
		super.doTearDown();
		activePage = null;
		propertySheet = null;
	}

	protected int countPropertySheetViews() {
		int count = 0;
		IViewReference[] views = activePage.getViewReferences();
		for (IViewReference ref : views) {
			if (ref.getId().equals(IPageLayout.ID_PROP_SHEET)) {
				count++;
			}
		}
		return count;
	}

	protected IAction getPinPropertySheetAction(PropertySheet propertySheet) {
		IActionBars actionBars = propertySheet.getViewSite().getActionBars();
		IToolBarManager toolBarManager = actionBars.getToolBarManager();
		IContributionItem[] items = toolBarManager.getItems();
		for (IContributionItem contributionItem : items) {
			if (contributionItem.getId() != null
					&& contributionItem.getId().startsWith(
							PIN_PROPERTY_SHEET_ACTION_ID_PREFIX)) {
				IAction action = ((ActionContributionItem) contributionItem)
						.getAction();
				return action;
			}
		}
		return null;
	}
}
