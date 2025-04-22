	@Test
	public void ensureThatClassAndIdareConsideredIfOnTheSameLevel() throws Exception {
		String css = "CTabFolder > Composite > Toolbar { color: black; }\n"
				+ "CTabFolder > Composite > .special { color: blue; font-weight: bold; }\n"
				+ "CTabFolder > Composite > #special { color: red; font-weight: bold; }\n";
		ViewCSS viewCSS = createViewCss(css);

		TestElement tabFolder = new TestElement("CTabFolder", engine);
		TestElement composite = new TestElement("Composite", tabFolder, engine);
		TestElement toolbar = new TestElement("Toolbar", composite, engine);

		CSSStyleDeclaration style = viewCSS.getComputedStyle(toolbar, null);
		assertEquals("black", style.getPropertyCSSValue("color").getCssText());

		toolbar.setClass("special");
		style = viewCSS.getComputedStyle(toolbar, null);
		assertEquals("blue", style.getPropertyCSSValue("color").getCssText());

		toolbar.setId("special");
		style = viewCSS.getComputedStyle(toolbar, null);
		assertEquals("red", style.getPropertyCSSValue("color").getCssText());

	}



