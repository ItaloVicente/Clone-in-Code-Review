
		FontData result = getFontData(
				fontProperties(
						addFontDefinitionMarker("org-eclipse-jface-bannerfont"),
						null, SWT.NORMAL),
						new FontData());

		assertEquals("Arial", result.getName());
