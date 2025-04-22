package org.eclipse.e4.ui.tests.css.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ButtonTextTransformTest extends TextTransformTest {

	@Override
	protected Control createControl(Composite parent) {
		return new Button(parent, SWT.PUSH);
	}

	@Override
	protected String getWidgetName() {
		return "Button";
	}

	@Override
	protected String getText(Control control) {
		return ((Button) control).getText();
	}

	@Override
	protected void setText(Control control, String string) {
		((Button) control).setText(string);
	}
}
