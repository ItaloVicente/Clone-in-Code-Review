package org.eclipse.ui.examples.views.properties.tabbed.logic.properties;

import org.eclipse.gef.examples.logicdesigner.model.LED;
import org.eclipse.jface.util.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ValueSection extends AbstractSection {

	Text valueText;

	private TextChangeHelper listener = new TextChangeHelper() {
		public void textChanged(Control control) {
			((LED) getElement()).setValue(
				Integer.parseInt(valueText.getText()));
		}
	};

	public void createControls(
		Composite parent,
		TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		
		Composite composite =
			getWidgetFactory().createFlatFormComposite(parent);
		FormData data;

		valueText = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		valueText.setLayoutData(data);

		CLabel valueLabel = getWidgetFactory().createCLabel(composite, "Value:"); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right =
			new FormAttachment(valueText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(valueText, 0, SWT.CENTER);
		valueLabel.setLayoutData(data);

		listener.startListeningForEnter(valueText);
		listener.startListeningTo(valueText);
	}

	public void refresh() {
		Assert.isTrue(getElement() instanceof LED);
		listener.startNonUserChange();
		try {
			valueText.setText(
				Integer.toString(((LED) getElement()).getValue()));
		} finally {
			listener.finishNonUserChange();
		}
	}
}
