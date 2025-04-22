package org.eclipse.ui.tests.views.properties.tabbed.override.tablist;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.IOverrideTestsItem;
import org.eclipse.ui.views.properties.tabbed.ITabItem;

public class OverrideTestsTabItem implements ITabItem {
	private IOverrideTestsItem item;
	private boolean selected = false;

	public OverrideTestsTabItem(IOverrideTestsItem anItem) {
		this.item = anItem;
	}

	public Image getImage() {
		return item.getImage();
	}

	public IOverrideTestsItem getItem() {
		return item;
	}

	public String getText() {
		return item.getText();
	}

	public boolean isIndented() {
		return false;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean newSelected) {
		this.selected = newSelected;
	}

}
