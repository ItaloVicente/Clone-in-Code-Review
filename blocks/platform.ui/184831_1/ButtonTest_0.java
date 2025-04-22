	@Test
	public void ensurePseudoAttributeAllowsToSelectionPushButton() {
		Button buttonToTest = createTestButton("Button[style~='SWT.CHECK'] { background-color: #FF0000; color: #0000FF }");

		assertEquals(RED, buttonToTest.getBackground().getRGB());
		assertEquals(BLUE, buttonToTest.getForeground().getRGB());

		Button unStyledButton = createTestButton("Button[style~='SWT.PUSH'] { background-color: #FF0000; color: #0000FF }");

		assertNotEquals(RED, unStyledButton.getBackground().getRGB());
		assertNotEquals(BLUE, unStyledButton.getForeground().getRGB());

	}
