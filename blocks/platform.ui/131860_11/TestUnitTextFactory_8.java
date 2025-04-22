package org.eclipse.jface.widgets;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.junit.Test;

public class TestUnitLabelFactory extends AbstractFactoryTest {

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
