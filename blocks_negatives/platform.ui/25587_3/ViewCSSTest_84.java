	public void testBug419482_order1() throws Exception
	{
		String css = "Shell > * > * { color: red; }\n"
				+ "Button { color: blue; }\n";
		ViewCSS viewCSS = createViewCss(css);

		final TestElement shell = new TestElement("Shell", engine);
		final TestElement composite = new TestElement("Composite", shell,
				engine);
		final TestElement button = new TestElement("Button", composite, engine);

		CSSStyleDeclaration buttonStyle = viewCSS.getComputedStyle(button, null);
		assertNotNull( buttonStyle );
		assertEquals( 1, buttonStyle.getLength() );
		assertEquals("color: blue;", buttonStyle.getCssText());
	}

	public void testBug419482_order2() throws Exception {
		String css = "Button { color: blue; }\n"
				+ "Shell > * > * { color: red; }\n";
		ViewCSS viewCSS = createViewCss(css);

		final TestElement shell = new TestElement("Shell", engine);
		final TestElement composite = new TestElement("Composite", shell,
				engine);
		final TestElement button = new TestElement("Button", composite, engine);

		CSSStyleDeclaration buttonStyle = viewCSS
				.getComputedStyle(button, null);
		assertNotNull(buttonStyle);
		assertEquals(1, buttonStyle.getLength());
		assertEquals("color: red;", buttonStyle.getCssText());
	}

	public void testBug419482_higherSpecificity() throws Exception {
		String css = "Shell > * > Button { color: blue; }\n"
				+ "Shell > * > * { color: red; }\n";
		ViewCSS viewCSS = createViewCss(css);

		final TestElement shell = new TestElement("Shell", engine);
		final TestElement composite = new TestElement("Composite", shell,
				engine);
		final TestElement button = new TestElement("Button", composite, engine);

		CSSStyleDeclaration buttonStyle = viewCSS
				.getComputedStyle(button, null);
		assertNotNull(buttonStyle);
		assertEquals(1, buttonStyle.getLength());
		assertEquals("color: blue;", buttonStyle.getCssText());
	}

