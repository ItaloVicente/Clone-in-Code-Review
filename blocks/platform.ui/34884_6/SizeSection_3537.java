package org.eclipse.ui.examples.views.properties.tabbed.logic.properties;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.examples.logicdesigner.model.LogicSubpart;
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

public class SizeSection
	extends AbstractSection {

	Text widthText;

	Text heightText;

	private TextChangeHelper listener = new TextChangeHelper() {

		public void textChanged(Control control) {
			Dimension dimension = new Dimension();
			dimension.width = Integer.parseInt(widthText.getText());
			dimension.height = Integer.parseInt(heightText.getText());
			((LogicSubpart) getElement()).setSize(dimension);
		}
	};

	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory()
			.createFlatFormComposite(parent);
		FormData data;

		widthText = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		widthText.setLayoutData(data);

		CLabel widthLabel = getWidgetFactory()
			.createCLabel(composite, "Width:"); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(widthText,
			-ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(widthText, 0, SWT.CENTER);
		widthLabel.setLayoutData(data);

		heightText = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(widthText, 0, SWT.LEFT);
		data.right = new FormAttachment(widthText, 0, SWT.RIGHT);
		data.top = new FormAttachment(widthText,
			ITabbedPropertyConstants.VSPACE, SWT.BOTTOM);
		heightText.setLayoutData(data);

		CLabel heightLabel = getWidgetFactory().createCLabel(composite,
			"Height:"); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(heightText,
			-ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(heightText, 0, SWT.CENTER);
		heightLabel.setLayoutData(data);

		listener.startListeningForEnter(heightText);
		listener.startListeningTo(heightText);
		listener.startListeningForEnter(widthText);
		listener.startListeningTo(widthText);
	}

	public void refresh() {
		Assert.isTrue(getElement() instanceof LogicSubpart);
		listener.startNonUserChange();
		try {
			Dimension dimension = ((LogicSubpart) getElement()).getSize();
			widthText.setText(Integer.toString(dimension.width));
			heightText.setText(Integer.toString(dimension.height));
		} finally {
			listener.finishNonUserChange();
		}
	}
}
