	public void testSelectorMatchOneOf() throws Exception {
		TestCSSEngine engine = new TestCSSEngine();
		final Element E = new TestElement("E", engine);
		engine.setElementProvider(new IElementProvider() {
			@Override
			public Element getElement(Object element, CSSEngine engine) {
				return E;
			}
		});
		Selector selector = engine.parseSelectors("E[a~='B']").item(0);

		E.setAttribute("a", "B ABC");
		assertTrue(engine.matches(selector, new Object(), null));

		E.setAttribute("a", "ABC B");
		assertTrue(engine.matches(selector, new Object(), null));

		E.setAttribute("a", "ABC");
		assertFalse(engine.matches(selector, new Object(), null));

		E.setAttribute("a", "B");
		assertTrue(engine.matches(selector, new Object(), null));
	}
