
	@Test
	public void testHasAttribute() {
		Widget widget = createTestLabel("");
		assertTrue(engine.getElement(widget).hasAttribute("style"));
		assertFalse(engine.getElement(widget).hasAttribute("fooBarAttribute"));
	}
