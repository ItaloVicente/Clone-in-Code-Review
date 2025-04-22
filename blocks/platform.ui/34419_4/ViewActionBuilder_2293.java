package org.eclipse.ui.internal;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.internal.e4.compatibility.E4Util;
import org.eclipse.ui.services.IServiceLocator;

public class ViewActionBars extends SubActionBars {

	public ViewActionBars(IActionBars parent,
 final IServiceLocator serviceLocator) {
		super(parent, serviceLocator);
	}

	@Override
	public IMenuManager getMenuManager() {
		E4Util.unsupported("ViewActionBars"); //$NON-NLS-1$
		return null;
	}

	@Override
	public IToolBarManager getToolBarManager() {
		E4Util.unsupported("ViewActionBars"); //$NON-NLS-1$
		return null;
	}

	@Override
	public void updateActionBars() {
		E4Util.unsupported("ViewActionBars"); //$NON-NLS-1$
		getStatusLineManager().update(false);
		fireActionHandlersChanged();
	}
}
