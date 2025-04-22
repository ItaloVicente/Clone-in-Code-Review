package org.eclipse.ui.quickaccess;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.quickaccess.QuickAccessEntry;

public interface IQuickAccessElement {

	String getLabel();

	ImageDescriptor getImageDescriptor();

	String getId();

	void execute();

	String getSortLabel();

	IQuickAccessProvider getProvider();

	QuickAccessMatch match(String filter, IQuickAccessProvider providerForMatching);

}
