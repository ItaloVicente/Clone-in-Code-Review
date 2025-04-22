package org.eclipse.ui.examples.views.properties.tabbed.logic.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class NameSection
	extends AbstractSection {

	Text nameText;

	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory()
			.createFlatFormComposite(parent);
		FormData data;

		nameText = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		nameText.setEditable(false);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		nameText.setLayoutData(data);

		CLabel nameLabel = getWidgetFactory().createCLabel(composite, "Name:"); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(nameText,
			-ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(nameText, 0, SWT.CENTER);
		nameLabel.setLayoutData(data);
	}

	public void refresh() {
		nameText.setText((getElement()).toString());
	}
}
