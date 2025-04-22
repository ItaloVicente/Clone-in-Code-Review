package org.eclipse.ui.tests.views.properties.tabbed.override.tablist;

import org.eclipse.ui.tests.views.properties.tabbed.model.Element;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.EmptyItem;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.IOverrideTestsItem;

public class EmptyTabList extends AbstractTabList {

	private IOverrideTestsItem[] sampleViewItems;

	public boolean appliesTo(Element element) {
		if (element == null) {
			return true;
		}
		return false;
	}

	public IOverrideTestsItem[] getItems() {
		if (sampleViewItems == null) {
			sampleViewItems = new IOverrideTestsItem[] { new EmptyItem() };
		}
		return sampleViewItems;
	}

}
