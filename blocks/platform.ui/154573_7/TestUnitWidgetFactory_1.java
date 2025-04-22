package org.eclipse.jface.tests.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.junit.Test;

public class TestUnitWidgetFactory extends AbstractFactoryTest {
	@Test
	public void createsWidgetWithNullsWhenNothingSet() {
		Label testLabel = LabelFactory.newLabel(SWT.NONE).create(shell);

		assertNull(testLabel.getData());

		assertEquals(testLabel, shell.getChildren()[0]);
	}

	@Test
	public void createsDifferentWidgetsWithSameFactory() {
		LabelFactory testFactory = LabelFactory.newLabel(SWT.NONE);

		Label label1 = testFactory.create(shell);
		Label label2 = testFactory.create(shell);

		assertNotSame(label1, label2);
		assertEquals(label1, shell.getChildren()[0]);
		assertEquals(label2, shell.getChildren()[1]);
	}

	@Test
	public void setsData() {
		String data = new String("myData");
		Label testLabel = LabelFactory.newLabel(SWT.NONE).data(data).create(shell);

		assertEquals(data, testLabel.getData());
	}
}
