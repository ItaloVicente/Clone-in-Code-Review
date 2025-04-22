package org.eclipse.e4.ui.tests.css.swt;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

public abstract class TextTransformTest extends CSSSWTTestCase {


	protected abstract String getWidgetName();

	protected abstract Control createControl(Composite parent);

	protected abstract String getText(Control control);

	protected abstract void setText(Control control, String string);

	private Control createTestControl(String styleSheet) {
		engine = createEngine(styleSheet, display);

		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);

		Control controlToTest = createControl(shell);
		setText(controlToTest, "Some label text");

		engine.applyStyles(shell, true);

		shell.pack();
		return controlToTest;
	}

	@Test
	public void testTextTransformCapitalize() {
		Control controlToTest = createTestControl(getWidgetName()
				+ " { text-transform: capitalize; }");
		assertEquals("Some Label Text", getText(controlToTest));
	}

	@Test
	public void testTextTransformUpperCase() {
		Control controlToTest = createTestControl(getWidgetName()
				+ " { text-transform: uppercase; }");
		assertEquals("SOME LABEL TEXT", getText(controlToTest));
	}

	@Test
	public void testTextTransformLowerCase() {
		Control controlToTest = createTestControl(getWidgetName()
				+ " { text-transform: lowercase; }");
		assertEquals("some label text", getText(controlToTest));
	}

}
