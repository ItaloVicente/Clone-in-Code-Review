
	@Test
	public void testSelectorAttributeIs() throws Exception {
		TestCSSEngine engine = engineWhichProducesElementsWithAttributeA();
		Selector selector = engine.parseSelectors("E[a='B']").item(0);
		assertFalse(engine.matches(selector, "ABC", null));
		assertTrue(engine.matches(selector, "B", null));
	}

	@Test
	public void testSelectorAttributeIs_EmptySting() throws Exception {
		TestCSSEngine engine = engineWhichProducesElementsWithAttributeA();
		Selector selector = engine.parseSelectors("E[a='']").item(0);
		assertFalse(engine.matches(selector, "ABC", null));
		assertTrue(engine.matches(selector, "", null));
	}

	@Test
	public void testSelectorAttributeIs_NotPresent() throws Exception {
		TestCSSEngine engine = engineWhichProducesElementsWithAttributeA();
		Selector selector = engine.parseSelectors("E[b='']").item(0);
		assertFalse(engine.matches(selector, "ABC", null));
		assertFalse(engine.matches(selector, "", null));
	}


	private TestCSSEngine engineWhichProducesElementsWithAttributeA() {
		TestCSSEngine engine = new TestCSSEngine();
		engine.setElementProvider((element, aEngine) -> {
			Element e = new TestElement("E", aEngine);
			e.setAttribute("a", element.toString());
			return e;
		});
		return engine;
	}

