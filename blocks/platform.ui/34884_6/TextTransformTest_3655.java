package org.eclipse.e4.ui.tests.css.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
 
public class TextTextTransformTest extends TextTransformTest {
		
	protected Control createControl(Composite parent) {
		return new Text(parent, SWT.SINGLE);
	}
	
	protected String getWidgetName() {
		return "Text";
	}

	protected String getText(Control control) {
		return ((Text) control).getText();
	}
	
	protected void setText(Control control, String string) {
		((Text) control).setText(string);
	}
}
