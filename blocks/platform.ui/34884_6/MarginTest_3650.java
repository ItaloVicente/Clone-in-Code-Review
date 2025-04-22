package org.eclipse.e4.ui.tests.css.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
 
public class LabelTextTransformTest extends TextTransformTest {
		
	protected Control createControl(Composite parent) {
		return new Label(parent, SWT.LEAD);
	}
	
	protected String getWidgetName() {
		return "Label";
	}

	protected String getText(Control control) {
		return ((Label) control).getText();
	}
	
	protected void setText(Control control, String string) {
		((Label) control).setText(string);
	}
}
