	@Test
	public void testCreateKeyWhenFontByDefinition() {
		CSS2FontProperties fontProperties = null;
		try {
			fontProperties = fontProperties("#font-by-definition", 12, SWT.ITALIC);
		} catch (Exception e) {
			fail("FontProperties should not throw exception");
		}
