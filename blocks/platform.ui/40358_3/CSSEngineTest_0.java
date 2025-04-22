	@Test
	public void testSelectorMatchOneOf() throws Exception {
		TestCSSEngine engine = new TestCSSEngine();
		engine.setElementProvider(new IElementProvider() {
			@Override
			public Element getElement(Object element, CSSEngine engine) {
				Element e = new TestElement("E", engine);
				e.setAttribute("a", element.toString());
				return e;
			}
		});
		Selector selector = engine.parseSelectors("E[a~='B']").item(0);
		assertTrue(engine.matches(selector, "B AB", null));
		assertTrue(engine.matches(selector, "BC B", null));
		assertFalse(engine.matches(selector, "ABC", null));
		assertTrue(engine.matches(selector, "B", null));
	}
