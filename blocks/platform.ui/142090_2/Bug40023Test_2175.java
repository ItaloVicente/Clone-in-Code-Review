		Shell shell = window.getShell();
		MenuItem windowMenu = getMenuItem(shell.getMenuBar().getItems(),
				"&Window"); //$NON-NLS-1$
		MenuItem lockToolBarsMenuItem = getMenuItem(windowMenu.getMenu()
				.getItems(), "Lock the &Toolbars"); //$NON-NLS-1$
		assertTrue("Checkbox menu item is not checked.", lockToolBarsMenuItem //$NON-NLS-1$
				.getSelection());
	}
