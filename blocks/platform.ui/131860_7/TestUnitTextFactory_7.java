package org.eclipse.jface.widgets;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class TestUnitLabelFactory {
	private static Shell shell;
	private static Image image = new Image(null, new Rectangle(1, 1, 1, 1));

	@Before
	public void setup() {
		shell = new Shell();
	}

	@After
	public void tearDown() {
		shell.dispose();
	}

	@AfterClass
	public static void classTearDown() {
		image.dispose();
		shell.dispose();
	}

	@Test
	public void createsLabel() {
		Label label = LabelFactory.newLabel(SWT.WRAP).create(shell);

		assertEquals(shell, label.getParent());
		assertEquals(SWT.WRAP, label.getStyle() & SWT.WRAP);
	}

	@Test
	public void createsLabelWithAllProperties() {
		Label label = LabelFactory.newLabel(SWT.NONE).text("Test Label").image(image).align(SWT.RIGHT).create(shell);

		assertEquals("Test Label", label.getText());
		assertEquals(image, label.getImage());
		assertEquals(SWT.RIGHT, label.getAlignment() & SWT.RIGHT);
	}
}
