package org.eclipse.ui.tests.views.properties.tabbed.override.tablist;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.tests.views.properties.tabbed.model.Element;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.IOverrideTestsItem;
import org.eclipse.ui.views.properties.tabbed.ITabItem;

public abstract class AbstractTabList implements IOverrideTestsTabList {

	private Composite composite;

	private int selectedTabItem;

	public boolean appliesTo(Element element) {
		return false;
	}

	public void createControls(Composite parent) {
		this.composite = parent;
		OverrideTestsTabItem activeTab = (OverrideTestsTabItem) (getTabs()[selectedTabItem]);
		activeTab.getItem().createControls(parent);
	}

	public void dispose() {
		OverrideTestsTabItem activeTab = (OverrideTestsTabItem) (getTabs()[selectedTabItem]);
		activeTab.getItem().dispose();
	}

	public ITabItem[] getTabs() {
		IOverrideTestsItem[] items = getItems();
		OverrideTestsTabItem[] tabs = new OverrideTestsTabItem[items.length];

		for (int i = 0; i < items.length; i++) {
			tabs[i] = new OverrideTestsTabItem(items[i]);
			if (i == selectedTabItem) {
				tabs[i].setSelected(true);
			}
		}

		return tabs;
	}

	public void selectionChanged(Element element) {
		ITabItem[] tabs = getTabs();
		for (int i = 0; i < tabs.length; i++) {
			if (tabs[i].getText().equals(element.getName())) {
				selectedTabItem = i;
				break;
			}
		}
	}

	public void selectTab(int index) {
		if (selectedTabItem == index) {
			return;
		}
		OverrideTestsTabItem activeTab = (OverrideTestsTabItem) (getTabs()[selectedTabItem]);
		activeTab.getItem().dispose();

		selectedTabItem = index;

		activeTab = (OverrideTestsTabItem) (getTabs()[selectedTabItem]);
		activeTab.getItem().createControls(composite);
		composite.layout(true);
	}
}
