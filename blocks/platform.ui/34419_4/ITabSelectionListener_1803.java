package org.eclipse.ui.views.properties.tabbed;

import org.eclipse.swt.graphics.Image;

public interface ITabItem {

	public Image getImage();

	public String getText();

	public boolean isSelected();

	public boolean isIndented();
}
