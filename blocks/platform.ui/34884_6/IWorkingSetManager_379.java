
package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;

public interface IWorkingSetElementAdapter {

	IAdaptable[] adaptElements(IWorkingSet ws, IAdaptable[] elements);

	void dispose();
}

