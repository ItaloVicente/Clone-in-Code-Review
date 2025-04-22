package org.eclipse.ui.views.properties.tabbed;

public class AbstractOverridableTabListPropertySection
	extends AbstractPropertySection
	implements IOverridableTabList {

	public ITabItem[] getTabs() {
		return new ITabItem[] {};
	}

	public void selectTab(int tab) {
	}
}
