		Shell shell = searchField.getQuickAccessShell();
		assertFalse("Quick access dialog should not be visible yet", shell.isVisible());
		handlerService
		.executeCommand("org.eclipse.ui.window.quickAccess", null); //$NON-NLS-1$
		assertTrue("Quick access dialog should be visible now", shell.isVisible());
	}

	/**
	 * Tests that typing in the text field opens the shell
	 */
	public void testOpenByText(){
		Shell shell = searchField.getQuickAccessShell();
		assertFalse("Quick access dialog should not be visible yet", shell.isVisible());
		Text text = searchField.getQuickAccessSearchText();
		text.setText("Test");
		assertTrue("Quick access dialog should be visible now", shell.isVisible());
