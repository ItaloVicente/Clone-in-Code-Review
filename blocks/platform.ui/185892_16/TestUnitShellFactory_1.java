	@Test
	public void createsShellInDisplayWithText() {
		Display display = Display.getDefault();
		Shell myShell = ShellFactory.newShell(SWT.BORDER).text("Test").create(display);

		assertEquals("Test", myShell.getText());
		assertNull(myShell.getParent());
		assertEquals(display, myShell.getDisplay());
	}

