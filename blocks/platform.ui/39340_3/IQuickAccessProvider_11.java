package org.eclipse.ui.quickaccess;

import org.eclipse.e4.core.contexts.IEclipseContext;

public interface IQuickAccessProvider {
	IQuickAccessElement[] getElements();

	IQuickAccessElement getElementForId(String id);

	void reset();

	void setContext(IEclipseContext context);
}
