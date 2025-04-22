package org.eclipse.jface.tests.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jface.widgets.ShellFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

public class TestUnitShellFactory extends AbstractFactoryTest {

	@Test
	public void createsShell() {
		Shell myShell = ShellFactory.createShell(SWT.BORDER).create(shell);
		assertEquals(shell, myShell.getParent());
	}

	@Test
	public void createsShellWithAllProperties() {
		Shell myShell = ShellFactory.createShell(SWT.BORDER).text("Test").enabled(false).create(shell);

		assertEquals("Test", myShell.getText());
		assertFalse(myShell.getEnabled());
	}

	@Test
	public void createsShellWithMenuBar() {
		final Menu[] menu = new Menu[1];
		Shell myShell = ShellFactory.createShell(SWT.BORDER).menuBar(shell -> {
			menu[0] = new Menu(shell, SWT.BAR);
			return menu[0];
		}).create(shell);

		assertEquals(menu[0], myShell.getMenuBar());
	}

	@Test
	public void createsMinimizedShell() {
		Shell myShell = ShellFactory.createShell(SWT.BORDER).minimized(true).create(shell);

		assertTrue(myShell.getMinimized());
	}

	@Test
	public void createsMaximizedShell() {
		Shell myShell = ShellFactory.createShell(SWT.BORDER).maximized(true).create(shell);

		assertTrue(myShell.getMaximized());
	}

	@Test
	public void createsFullScreenShell() {
		Shell myShell = ShellFactory.createShell(SWT.BORDER).fullScreen(true).create(shell);

		assertTrue(myShell.getFullScreen());
	}
}
