
package org.eclipse.ui.statushandlers;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorSupportProvider;
import org.eclipse.jface.util.Policy;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class AbstractStatusAreaProvider extends ErrorSupportProvider {

	public abstract Control createSupportArea(Composite parent,
			StatusAdapter statusAdapter);

	@Override
	public final Control createSupportArea(Composite parent, IStatus status) {
		return createSupportArea(parent, new StatusAdapter(status));
	}

	public boolean validFor(StatusAdapter statusAdapter) {
		return true;
	}
}
