package org.eclipse.ui.views.properties.tabbed;

public interface IOverridableTabList {

	public ITabItem[] getTabs();

	public void selectTab(int index);
}
