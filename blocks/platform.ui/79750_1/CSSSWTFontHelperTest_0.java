		assertEquals("Times", result.getName());
		assertEquals(11, result.getHeight());
		assertEquals(SWT.ITALIC | SWT.BOLD, result.getStyle());
	}

	@Test
	public void testGetFontDataWithoutOldFont() {
		FontData result = getFontData(fontProperties("Times", 11, CSS_ITALIC, CSS_BOLD),
				null);

		assertEquals("Times", result.getName());
		assertEquals(11, result.getHeight());
		assertEquals(SWT.ITALIC | SWT.BOLD, result.getStyle());
	}

	@Test
	public void testGetFontDataStyledFont() {
		FontData result = getFontData(fontProperties("Times", 11, "normal", "normal"),
				new FontData("Courier", 11, SWT.ITALIC | SWT.BOLD));

