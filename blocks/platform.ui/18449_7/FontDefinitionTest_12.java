		assertEquals("categoryId", definition.getCategoryId());
		assertEquals("name", definition.getName());
		assertTrue(definition.getDescription().startsWith("description"));
		assertTrue(definition.getDescription().endsWith(definition.getOverriddenLabel()));
		assertTrue(definition.isOverridden());
	}

	public void testFontDefinitionWhenNameCategoryIdAndDescriptionOverridden()
			throws Exception {
		CSSEngine engine = createEngine("FontDefinition#org-eclipse-jface-bannerfont {font-family: 'Times';font-size: 12;font-style: italic;" +
						" label:'nameOverridden'; category: '#categoryIdOverridden'; description: 'descriptionOverridden'}", display);
		FontDefinition definition = fontDefinition("org.eclipse.jface.bannerfont", "name", "categoryId", "description");

		assertNull(definition.getValue());
		assertFalse(definition.isOverridden());

		engine.applyStyles(definition, true);

		assertNotNull(definition.getValue());
		assertEquals("Times", definition.getValue()[0].getName());
		assertEquals(12, definition.getValue()[0].getHeight());
		assertEquals(SWT.ITALIC, definition.getValue()[0].getStyle());
		assertEquals("categoryIdOverridden", definition.getCategoryId());
		assertEquals("nameOverridden", definition.getName());
		assertTrue(definition.getDescription().startsWith("descriptionOverridden"));
		assertTrue(definition.getDescription().endsWith(definition.getOverriddenLabel()));
