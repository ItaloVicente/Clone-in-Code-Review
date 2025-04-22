package org.eclipse.ui.tests.views.properties.tabbed.override.items;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public interface IOverrideTestsItem {

	public void createControls(Composite parent);

	public void dispose();

	public Composite getComposite();

	public Class getElement();

	public Image getImage();

	public String getText();
}
