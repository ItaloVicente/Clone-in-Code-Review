package org.eclipse.e4.ui.tests.css.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
 
public class ButtonTextTransformTest extends TextTransformTest {
		
	protected Control createControl(Composite parent) {
		return new Button(parent, SWT.PUSH);
	}
	
	protected String getWidgetName() {
		return "Button";
	}

	protected String getText(Control control) {
		return ((Button) control).getText();
	}
	
	protected void setText(Control control, String string) {
		((Button) control).setText(string);
	}
}
