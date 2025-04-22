
package org.eclipse.ui.tests.propertysheet;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.views.properties.NewPropertySheetHandler;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertyShowInContext;

public class TestNewPropertySheetHandler extends NewPropertySheetHandler {

	public static final String ID = NewPropertySheetHandler.ID + "Test";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		return super.execute(event);
	}

	@Override
	public PropertyShowInContext getShowInContext(ExecutionEvent event)
			throws ExecutionException {
		return super.getShowInContext(event);
	}

	@Override
	protected PropertySheet findPropertySheet(ExecutionEvent event,
			PropertyShowInContext context) throws PartInitException,
			ExecutionException {
		return super.findPropertySheet(event, context);
	}
}
