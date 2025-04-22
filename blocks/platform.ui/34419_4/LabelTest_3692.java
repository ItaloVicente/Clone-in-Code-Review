
package org.eclipse.e4.ui.tests.css.swt;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

public class InnerClassElementTest extends CSSSWTTestCase {


	class CustomComposite extends Composite {
		public CustomComposite(Composite parent, int style) {
			super(parent, style);
		}
	}

	@Override
	protected Label createTestLabel(String styleSheet) {
		engine = createEngine(styleSheet, display);

		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);

		CustomComposite composite = new CustomComposite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());

		Label labelToTest = new Label(composite, SWT.NONE);
		labelToTest.setText("Some label text");

		engine.applyStyles(labelToTest, true);

		return labelToTest;
	}

	@Test
	public void testInnerClassElement() {
		Label label = createTestLabel("InnerClassElementTest-CustomComposite Label { color: #00ffa0; }");

		assertEquals(0x00, label.getForeground().getRed());
		assertEquals(0xff, label.getForeground().getGreen());
		assertEquals(0xa0, label.getForeground().getBlue());
	}
}
