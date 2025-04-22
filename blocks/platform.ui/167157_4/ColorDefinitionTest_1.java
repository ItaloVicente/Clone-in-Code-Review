	@Test
	public void testSetColorDefinitionWithSystemColor() {
		CSSEngine engine = createEngine("ColorDefinition#ACTIVE_HYPERLINK_COLOR{color: '#COLOR-LIST-SELECTION'}",
				display);
		ColorDefinition definition = colorDefinition("ACTIVE_HYPERLINK_COLOR", "name", "categoryId", "description");

		assertEquals(new RGB(0, 0, 0), definition.getValue());
		assertFalse(definition.isOverridden());

		engine.applyStyles(definition, true);

		assertEquals(display.getSystemColor(SWT.COLOR_LIST_SELECTION).getRGB(), definition.getValue());
		assertTrue(definition.isOverridden());
		engine.dispose();
	}

