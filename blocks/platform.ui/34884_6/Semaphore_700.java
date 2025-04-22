
package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.util.Util;

public class SelectionConversionService implements ISelectionConversionService {

	@Override
	public IStructuredSelection convertToResources(
			IStructuredSelection originalSelection) {
		Class resourceClass = LegacyResourceSupport.getResourceClass();
		if (resourceClass == null) {
			return originalSelection;
		}

		List result = new ArrayList();
		Iterator elements = originalSelection.iterator();

		while (elements.hasNext()) {
			Object currentElement = elements.next();
            Object resource = Util.getAdapter(currentElement, resourceClass);
            if (resource != null) {
            	result.add(resource);   
            }
		}

		if (result.isEmpty()) {
			return StructuredSelection.EMPTY;
		}
		return new StructuredSelection(result.toArray());
	}

}
