		FontData result = getFontData(
				fontProperties(addFontDefinitionMarker("org-eclipse-jface-bannerfont"), null, null, CSS_BOLD),
				new FontData());

		assertEquals("Arial", result.getName());
		assertEquals(15, result.getHeight());
		assertEquals(SWT.ITALIC | SWT.BOLD, result.getStyle());
	}

	@Test
	public void testGetFontDataFromFontDefinition() {
		registerFontProviderWith("org.eclipse.jface.bannerfont", "Arial", 15, SWT.ITALIC | SWT.BOLD);

