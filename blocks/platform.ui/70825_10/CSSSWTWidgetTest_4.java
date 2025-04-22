
	@Test
	public void testHasAttribute() {
		Widget widget = createTestLabel("Label { }");
		String propertySetToEmptyStringKey = "empty-property";
		widget.setData(propertySetToEmptyStringKey, "");
		assertTrue(engine.getElement(widget).hasAttribute(propertySetToEmptyStringKey));
		assertFalse(engine.getElement(widget).hasAttribute("foo-bar-attribute"));
		assertNotNull(widget);
	}

	@Test
	public void testGetAttributeWithSwtStylesNull() {
		Widget widget = createTestLabel("Label { }");
		engine.setElementProvider((element, engine) -> new WidgetElementWithSwtStylesNull((Widget) element, engine));

		assertTrue(engine.getElement(widget).hasAttribute("style"));
		assertEquals("", engine.getElement(widget).getAttribute("style"));
	}

	@Test
	public void testGetAttributeWithAttributeTypeNull() {
		Widget widget = createTestLabel("Label { }");
		engine.setElementProvider(
				(element, engine) -> new SWTHTMLElementWithAttributeTypeNull((Widget) element, engine));

		assertTrue(engine.getElement(widget).hasAttribute("type"));
		assertEquals("", engine.getElement(widget).getAttribute("type"));
	}

	@Test(expected = AssertionFailedException.class)
	public void testGetAttributeWithAttributeSupplierReturningNull() {
		Widget widget = createTestLabel("Label { }");
		engine.setElementProvider(
				(element, engine) -> new WidgetElementWithSupplierReturningNull((Widget) element, engine));

		engine.getElement(widget).getAttribute("style");

	}
