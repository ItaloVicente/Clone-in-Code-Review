package org.eclipse.ui.tests.views.properties.tabbed.override.tablist;

import org.eclipse.ui.tests.views.properties.tabbed.model.Element;
import org.eclipse.ui.tests.views.properties.tabbed.model.Error;
import org.eclipse.ui.tests.views.properties.tabbed.model.Information;
import org.eclipse.ui.tests.views.properties.tabbed.model.Warning;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.ErrorItem;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.IOverrideTestsItem;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.InformationItem;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.WarningItem;

public class BasicTabList extends AbstractTabList {

	private IOverrideTestsItem[] overrideTestsItems;

	public boolean appliesTo(Element element) {
		return ((element instanceof Information) ||
				(element instanceof Warning) || (element instanceof Error));
	}

	public IOverrideTestsItem[] getItems() {
		if (overrideTestsItems == null) {
			overrideTestsItems = new IOverrideTestsItem[] {
					new InformationItem(), new WarningItem(), new ErrorItem() };
		}
		return overrideTestsItems;
	}

}
