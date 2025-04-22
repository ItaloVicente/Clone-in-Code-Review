package org.eclipse.ui.tests.views.properties.tabbed.override.tablist;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.tests.views.properties.tabbed.model.Element;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.IOverrideTestsItem;
import org.eclipse.ui.views.properties.tabbed.IOverridableTabList;

public interface IOverrideTestsTabList extends IOverridableTabList {

	public boolean appliesTo(Element element);

	public void createControls(Composite parent);

	public void dispose();

	public IOverrideTestsItem[] getItems();

	public void selectionChanged(Element element);

}
