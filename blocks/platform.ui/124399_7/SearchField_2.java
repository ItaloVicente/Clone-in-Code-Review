package org.eclipse.ui.internal.quickaccess;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public abstract class QuickAccessUiElementProvider extends QuickAccessProvider {

	protected final IWorkbenchWindow activeWindow;

	public QuickAccessUiElementProvider() {
		activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}
}
