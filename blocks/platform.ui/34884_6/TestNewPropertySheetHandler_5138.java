
package org.eclipse.ui.tests.propertysheet;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertyShowInContext;

public class ShowInPropertySheetTest extends AbstractPropertySheetTest {

	public ShowInPropertySheetTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();

		propertySheet = (PropertySheet) activePage
				.showView(IPageLayout.ID_PROP_SHEET);
	}

	@Override
	protected void doTearDown() throws Exception {
		super.doTearDown();
	}

	public void testGetIShowInTargetAdapter() {
		Object adapter = propertySheet.getAdapter(IShowInTarget.class);
		assertNotNull("No IShowInTarget adapter returned", adapter);
		assertTrue(adapter instanceof IShowInTarget);
	}

	public void testShowInPropertySheet() {
		IShowInTarget showInTarget = (IShowInTarget) propertySheet
				.getAdapter(IShowInTarget.class);
		ShowInContext context = new PropertyShowInContext(activePage
				.getActivePart(), StructuredSelection.EMPTY);
		assertTrue(showInTarget.show(context));
	}

	public void testShowInPropertySheetWithNull() {
		IShowInTarget showInTarget = (IShowInTarget) propertySheet
				.getAdapter(IShowInTarget.class);
		assertFalse(showInTarget.show(null));
	}

	public void testShowInPropertySheetWithNullContext() {
		IShowInTarget showInTarget = (IShowInTarget) propertySheet
				.getAdapter(IShowInTarget.class);
		assertFalse(showInTarget.show(new ShowInContext(null, null)));
	}

	public void testShowInPropertySheetWithNullPart() {
		IShowInTarget showInTarget = (IShowInTarget) propertySheet
				.getAdapter(IShowInTarget.class);
		assertFalse(showInTarget.show(new ShowInContext(new Object(),
				StructuredSelection.EMPTY)));
	}
}
