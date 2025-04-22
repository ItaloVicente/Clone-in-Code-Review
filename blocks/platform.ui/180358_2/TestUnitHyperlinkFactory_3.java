package org.eclipse.jface.tests.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jface.widgets.HyperlinkFactory;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.junit.Test;

public class TestUnitHyperlinkFactory extends AbstractFactoryTest {

	@Test
	public void createsHyperlink() {
		Hyperlink hyperlink = HyperlinkFactory.newHyperlink(SWT.NONE).create(shell);

		assertEquals(shell, hyperlink.getParent());
		assertEquals(SWT.NONE, hyperlink.getStyle() & SWT.NONE);
	}

	@Test
	public void createsHyperlinkWithAllProperties() {
		Hyperlink hyperlink = HyperlinkFactory.newHyperlink(SWT.NONE).text("Test Hyperlink").underline(true)
				.create(shell);

		assertEquals("Test Hyperlink", hyperlink.getText());
		assertTrue(hyperlink.isUnderlined());
	}
}
