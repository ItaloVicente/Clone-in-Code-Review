	@Test
	public void testUnset() {
		CSSEngine engine = createEngine("Button {background-color: unset;}", display);

		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		Button button = new Button(shell, SWT.NONE);
		Color red = display.getSystemColor(SWT.COLOR_RED);
		button.setBackground(red);

		engine.applyStyles(button, true);


		assertNotEquals(red.getRGB(), button.getBackground().getRGB());

		engine.dispose();
		shell.dispose();
	}

