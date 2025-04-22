package org.eclipse.ui.quickaccess;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
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

	void setWindow(MWindow window);

	void setApplication(MApplication application);

}
