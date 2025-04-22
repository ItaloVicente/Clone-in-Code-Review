package org.eclipse.ui.quickaccess;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.resource.ImageDescriptor;

public interface IQuickAccessProvider {

	String getId();

	String getName();

	ImageDescriptor getImageDescriptor();

	IQuickAccessElement[] getElements();

	IQuickAccessElement[] getElementsSorted();

	IQuickAccessElement getElementForId(String id);

	boolean isAlwaysPresent();

	void reset();

	void setContext(IEclipseContext context);
}
