	public void testGetFontDataWhenMissingWeightInCss() {
		FontData result = getFontData(fontProperties("Times", 11, CSS_ITALIC, null),
				new FontData("Courier", 5, SWT.BOLD));

		assertEquals("Times", result.getName());
		assertEquals(11, result.getHeight());
		assertEquals(SWT.ITALIC | SWT.BOLD, result.getStyle());
	}

	@Test
	public void testGetFontDataWhenMissingAllInCss() {
		FontData result = getFontData(fontProperties(null, null, null, null),
				new FontData("Courier", 11, SWT.ITALIC | SWT.BOLD));

		assertEquals("Courier", result.getName());
		assertEquals(11, result.getHeight());
		assertEquals(SWT.ITALIC | SWT.BOLD, result.getStyle());
	}

	@Test
	public void testGetFontDataWhenFontFamilyFromDefinitionAndOverwritingSize() {
		registerFontProviderWith("org.eclipse.jface.bannerfont", "Arial", 15, SWT.ITALIC | SWT.BOLD);
