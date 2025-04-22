
	@Test
	public void testHasAttribute() {
		Widget widget = createTestLabel("Label { }");
		String propertySetToEmptyStringKey = "empty-property";
		widget.setData(propertySetToEmptyStringKey, "");
		assertTrue(engine.getElement(widget).hasAttribute(propertySetToEmptyStringKey));
		assertFalse(engine.getElement(widget).hasAttribute("foo-bar-attribute"));
		assertNotNull(widget);
	}
