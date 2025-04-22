package org.eclipse.jface.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUnitControlFactory {

	private static Shell shell;

	@Before
	public void setup() {
		shell = new Shell();
	}

	@After
	public void tearDown() {
		shell.dispose();
	}

	@Test
	public void createsControlWithNullsWhenNothingSet() {
		Label testLabel = TestFactory.newTest().create(shell);

		assertTrue(testLabel.getEnabled());
		assertNull(testLabel.getLayoutData());
		assertNull(testLabel.getToolTipText());

		assertEquals(testLabel, shell.getChildren()[0]);
	}

	@Test
	public void createsDifferentControlsWithSameFactory() {
		TestFactory testFactory = TestFactory.newTest();

		Label label1 = testFactory.create(shell);
		Label label2 = testFactory.create(shell);

		assertNotEquals(label1, label2);
		assertEquals(label1, shell.getChildren()[0]);
		assertEquals(label2, shell.getChildren()[1]);
	}

	@Test
	public void createsControlWithProperties() {
		GridData gridData = new GridData();
		Label label = TestFactory.newTest().tooltip("toolTip").enabled(false).layoutData(gridData)
				.create(shell);

		assertFalse(label.getEnabled());
		assertEquals("toolTip", label.getToolTipText());
		assertEquals(gridData, label.getLayoutData());
	}

	static class TestFactory extends ControlFactory<TestFactory, Label> {

		protected TestFactory(int style) {
			super(TestFactory.class, parent -> new Label(parent, style));
		}

		public static TestFactory newTest() {
			return new TestFactory(SWT.NONE);
		}
	}
}
