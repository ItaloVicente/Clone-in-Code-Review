
package org.eclipse.ui.tests.propertysheet;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class TestPropertySheetPage extends PropertySheetPage implements
		IPropertySheetPage, IAdapterFactory {

	private ISelection fSelection;
	private IWorkbenchPart fPart;

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		fSelection = null;
		fPart = null;
		return this;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] { IPropertySheetPage.class };
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		fPart = part;
		fSelection = selection;
	}

	public ISelection getSelection() {
		return fSelection;
	}

	public IWorkbenchPart getPart() {
		return fPart;
	}
}
