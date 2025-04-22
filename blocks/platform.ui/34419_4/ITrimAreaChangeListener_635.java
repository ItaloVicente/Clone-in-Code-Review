
package org.eclipse.ui.internal;

import org.eclipse.jface.viewers.IStructuredSelection;

public interface ISelectionConversionService {

	public IStructuredSelection convertToResources(
			IStructuredSelection originalSelection);

}

