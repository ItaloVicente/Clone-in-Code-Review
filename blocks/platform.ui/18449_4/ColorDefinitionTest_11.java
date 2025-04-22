		assertEquals("name", definition.getName());
		assertEquals("categoryId", definition.getCategoryId());
		assertTrue(definition.getDescription().startsWith("description"));
		assertTrue(definition.getDescription().endsWith(definition.getOverriddenLabel()));
		assertTrue(definition.isOverridden());
	}

	public void testColorDefinitionWhenNameCategoryIdAndDescriptionOverridden()
			throws Exception {
		CSSEngine engine = createEngine("ColorDefinition#ACTIVE_HYPERLINK_COLOR{color: green;" +
				"label:'nameOverridden'; category:'#categoryIdOverridden'; description: 'descriptionOverridden'}", display);
		ColorDefinition definition = colorDefinition("ACTIVE_HYPERLINK_COLOR","name", "categoryId", "description");

		assertEquals(new RGB(0, 0, 0), definition.getValue());
		assertFalse(definition.isOverridden());

		engine.applyStyles(definition, true);

		assertEquals(new RGB(0, 128, 0), definition.getValue());
		assertEquals("nameOverridden", definition.getName());
		assertEquals("categoryIdOverridden", definition.getCategoryId());
		assertTrue(definition.getDescription().startsWith("descriptionOverridden"));
		assertTrue(definition.getDescription().endsWith(definition.getOverriddenLabel()));
