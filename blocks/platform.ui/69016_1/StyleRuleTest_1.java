
	@Test
	public void testGetCSSText() throws Exception {
		String css = "Label, * > Label { background-color: rgb(255, 2, 32); }";
		CSSStyleSheet styleSheet = ParserTestUtil.parseCss(css);
		CSSRuleList rules = styleSheet.getCssRules();
		CSSRule rule = rules.item(0);
		assertEquals(css, rule.getCssText());
	}

