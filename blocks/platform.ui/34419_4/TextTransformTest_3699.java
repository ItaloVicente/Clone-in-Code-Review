package org.eclipse.e4.ui.tests.css.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class TextTextTransformTest extends TextTransformTest {

	@Override
	protected Control createControl(Composite parent) {
		return new Text(parent, SWT.SINGLE);
	}

	@Override
	protected String getWidgetName() {
		return "Text";
	}

	@Override
	protected String getText(Control control) {
		return ((Text) control).getText();
	}

	@Override
	protected void setText(Control control, String string) {
		((Text) control).setText(string);
	}
}
