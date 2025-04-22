package org.eclipse.ui.views.properties.tabbed;

import org.eclipse.jface.viewers.IStructuredContentProvider;


public interface IOverridableTabListContentProvider extends IStructuredContentProvider {

	public void overrideTabs();
}
