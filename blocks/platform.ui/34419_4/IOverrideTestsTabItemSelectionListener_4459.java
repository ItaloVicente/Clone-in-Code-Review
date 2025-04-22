package org.eclipse.ui.tests.views.properties.tabbed.override.folders;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.tests.views.properties.tabbed.model.Element;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.IOverrideTestsItem;

public interface IOverrideTestsTabFolder {
	public void addItemSelectionListener(
			IOverrideTestsTabItemSelectionListener listener);

	public boolean appliesTo(Element element);

	public void createControls(Composite parent);

	public void dispose();

	public IOverrideTestsItem[] getItem();

	public void removeItemSelectionListener(
			IOverrideTestsTabItemSelectionListener listener);

	public void selectionChanged(Element element);
}
