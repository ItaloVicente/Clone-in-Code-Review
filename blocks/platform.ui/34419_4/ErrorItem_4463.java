package org.eclipse.ui.tests.views.properties.tabbed.override.items;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class EmptyItem implements IOverrideTestsItem {

	private Composite composite;

	public void createControls(Composite parent) {
		TabbedPropertySheetWidgetFactory factory = new TabbedPropertySheetWidgetFactory();
		composite = factory.createFlatFormComposite(parent);
		Label label = factory.createLabel(composite,
				"Empty Item (no selected element)"); //$NON-NLS-1$
		label.setLayoutData(new FormData());
	}

	public void dispose() {
		if (composite != null && !composite.isDisposed()) {
			composite.dispose();
			composite = null;
		}
	}

	public Composite getComposite() {
		return composite;
	}

	public Class getElement() {
		return null;
	}

	public Image getImage() {
		return null;
	}

	public String getText() {
		return "Empty Item"; //$NON-NLS-1$
	}
}
