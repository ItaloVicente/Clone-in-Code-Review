	public void testSelectorMatchOneOf() throws Exception {
		TestCSSEngine engine = new TestCSSEngine();
		SelectorList list = engine.parseSelectors("E[a~='B']");

		engine.setElementProvider(new IElementProvider() {
			@Override
			public Element getElement(Object element, CSSEngine engine) {
				Element result = new TestElement("E", engine);
				result.setAttribute("a", "B ABC");
				return result;
			}
		});
		assertTrue(engine.matches(list.item(0), new Object(), null));

		engine.setElementProvider(new IElementProvider() {
			@Override
			public Element getElement(Object element, CSSEngine engine) {
				Element result = new TestElement("E", engine);
				result.setAttribute("a", "ABC B");
				return result;
			}
		});
		assertTrue(engine.matches(list.item(0), new Object(), null));

		engine.setElementProvider(new IElementProvider() {
			@Override
			public Element getElement(Object element, CSSEngine engine) {
				Element result = new TestElement("E", engine);
				result.setAttribute("a", "ABC");
				return result;
			}
		});
		assertFalse(engine.matches(list.item(0), new Object(), null));
	}
