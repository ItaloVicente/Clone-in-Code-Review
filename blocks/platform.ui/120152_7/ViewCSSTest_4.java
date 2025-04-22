	@SuppressWarnings("unchecked")
	@Test
	public void testRuleCaching() throws Exception {
		String css = "Shell > * > * { color: red; }\n" + "Button { color: blue; }\n";
		CSSStyleSheet styleSheet = ParserTestUtil.parseCss(css);
		DocumentCSSImpl docCss = new DocumentCSSImpl();
		docCss.addStyleSheet(styleSheet);
		ViewCSSImpl viewCSS = new ViewCSSImpl(docCss);

		Class<?>[] NO_TYPES = new Class<?>[0];
		Object[] NO_ARGS = new Object[0];

		Field currentCombinedRulesField = ViewCSSImpl.class.getDeclaredField("currentCombinedRules");
		currentCombinedRulesField.setAccessible(true);

		assertNull(currentCombinedRulesField.get(viewCSS));

		final TestElement shell = new TestElement("Shell", engine);
		final TestElement composite = new TestElement("Composite", shell, engine);
		final TestElement button = new TestElement("Button", composite, engine);
		CSSStyleDeclaration buttonStyle = viewCSS.getComputedStyle(button, null);
		assertNotNull(buttonStyle);

		assertNotNull(currentCombinedRulesField.get(viewCSS));

		Method getCombinedRulesMethod = ViewCSSImpl.class.getDeclaredMethod("getCombinedRules", NO_TYPES);
		getCombinedRulesMethod.setAccessible(true);

		List<CSSRule> cssRules = (List<CSSRule>) getCombinedRulesMethod.invoke(viewCSS, NO_ARGS);
		assertSame(cssRules, getCombinedRulesMethod.invoke(viewCSS, NO_ARGS));

		css = "Shell > * > * { color: blue; }\n" + "Label { color: green; }\n";
		styleSheet = ParserTestUtil.parseCss(css);
		docCss.addStyleSheet(styleSheet);

		assertNull(currentCombinedRulesField.get(viewCSS));

		List<CSSRule> cssRules2 = (List<CSSRule>) getCombinedRulesMethod.invoke(viewCSS, NO_ARGS);
		assertNotSame(cssRules, cssRules2);
		assertTrue(cssRules2.size() > cssRules.size());
	}

