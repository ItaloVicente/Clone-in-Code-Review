package org.eclipse.jface.tests.widgets;

import static org.junit.Assert.assertEquals;

import org.eclipse.jface.widgets.GroupFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.junit.Test;

public class TestUnitGroupFactory extends AbstractFactoryTest {

	@Test
	public void createsGroup() {
		Group Group = GroupFactory.newGroup(SWT.SHADOW_NONE).create(shell);

		assertEquals(shell, Group.getParent());
		assertEquals(SWT.SHADOW_NONE, Group.getStyle() & SWT.SHADOW_NONE);
	}

	@Test
	public void createsGroupWithText() {
		Group Group = GroupFactory.newGroup(SWT.NONE).text("Test Group").create(shell);

		assertEquals("Test Group", Group.getText());
	}
}
