		assertEquals(SWT.ITALIC, fontData.getStyle());
	}

	@Ignore
	public void testSelectedPseudo() {
		Button buttonToTest = createTestButton("Button { color: #FF0000; }\n" + "Button:selected { color: #0000FF; }");
		assertEquals(RED, buttonToTest.getForeground().getRGB());
		buttonToTest.setSelection(true);
		engine.applyStyles(buttonToTest.getShell(), true);
		assertEquals(BLUE, buttonToTest.getForeground().getRGB());
