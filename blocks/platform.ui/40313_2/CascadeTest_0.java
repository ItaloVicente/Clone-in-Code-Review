
	@Test
	public void testBug458342_combine() throws Exception {
		String css1 = "Button { color: blue; }";
		String css2 = "Button { font-weight: bold; }";

		ViewCSS viewCSS = createViewCss(css1, css2);

		TestElement button = new TestElement("Button", engine);
		CSSStyleDeclaration style = viewCSS.getComputedStyle(button, null);
		assertEquals("blue", style.getPropertyCSSValue("color").getCssText());
		assertEquals("bold", style.getPropertyCSSValue("font-weight").getCssText());
	}

	@Test
	public void testBug458342_override() throws Exception {
		String css1 = "Button { color: blue; font-weight: bold; }";
		String css2 = "Button { color: black; }";

		ViewCSS viewCSS = createViewCss(css1, css2);

		TestElement button = new TestElement("Button", engine);
		CSSStyleDeclaration style = viewCSS.getComputedStyle(button, null);
		assertEquals("black", style.getPropertyCSSValue("color").getCssText());
		assertEquals("bold", style.getPropertyCSSValue("font-weight").getCssText());
	}
