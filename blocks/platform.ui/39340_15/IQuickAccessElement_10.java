package org.eclipse.ui.quickaccess;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IQuickAccessElement {

	String getLabel();

	ImageDescriptor getImageDescriptor();

	String getId();

	void execute();

	String getSortLabel();

	IQuickAccessProvider getProvider();

	QuickAccessMatch match(String filter, IQuickAccessProvider providerForMatching);

}
