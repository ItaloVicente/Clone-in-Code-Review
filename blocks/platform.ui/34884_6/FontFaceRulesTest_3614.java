package org.eclipse.e4.ui.tests.css.core.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.e4.ui.css.core.impl.dom.DocumentCSSImpl;
import org.eclipse.e4.ui.css.core.impl.dom.ViewCSSImpl;
import org.eclipse.e4.ui.css.swt.engine.CSSSWTEngineImpl;
import org.eclipse.e4.ui.tests.css.core.util.ParserTestUtil;
import org.eclipse.e4.ui.tests.css.core.util.TestElement;
import org.eclipse.swt.widgets.Display;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.ViewCSS;


public class CascadeTest {

	private Display display;
	private CSSSWTEngineImpl engine;

	@Before
	public void setUp() throws Exception {
		display = Display.getDefault();
		engine = new CSSSWTEngineImpl(display);
	}

	@Test
	public void testPosition() throws Exception {
		String css = "Button { color: blue; font-weight: bold; }\n"
				+ "Button { color: black }\n";
		ViewCSS viewCSS = createViewCss(css);

		TestElement button = new TestElement("Button", engine);
		CSSStyleDeclaration style = viewCSS.getComputedStyle(button, null);
		assertEquals("black", style.getPropertyCSSValue("color").getCssText());
		assertEquals("bold", style.getPropertyCSSValue("font-weight")
				.getCssText());
	}

	@Test
	public void testSpecificity() throws Exception {
		String css = "Label, Button.special { color: black; }\n"
				+ "Button { color: blue; font-weight: bold; }\n";
		ViewCSS viewCSS = createViewCss(css);

		TestElement button = new TestElement("Button", engine);
		CSSStyleDeclaration style = viewCSS.getComputedStyle(button, null);
		assertEquals("blue", style.getPropertyCSSValue("color").getCssText());

		button.setClass("special");
		style = viewCSS.getComputedStyle(button, null);
		assertEquals("black", style.getPropertyCSSValue("color").getCssText());
		assertEquals("bold", style.getPropertyCSSValue("font-weight")
				.getCssText());
	}

	@Test
	public void testSpecificities() throws Exception {
		String css = "* { color: black; }\n"
				+ "Button { color: blue; }\n"
				+ "Button[BORDER] { color: gray; }\n"
				+ "Button.special { color: green; }\n"
				+ "Button#myid { color: red; }\n";
		ViewCSS viewCSS = createViewCss(css);

		TestElement label = new TestElement("Label", engine);
		CSSStyleDeclaration style = viewCSS.getComputedStyle(label, null);
		assertEquals("black", style.getPropertyCSSValue("color").getCssText());

		TestElement button = new TestElement("Button", engine);
		style = viewCSS.getComputedStyle(button, null);
		assertEquals("blue", style.getPropertyCSSValue("color").getCssText());

		button.setAttribute("BORDER", "true");
		style = viewCSS.getComputedStyle(button, null);
		assertEquals("gray", style.getPropertyCSSValue("color").getCssText());

		button.setClass("special");
		style = viewCSS.getComputedStyle(button, null);
		assertEquals("green", style.getPropertyCSSValue("color").getCssText());

		button.setId("myid");
		style = viewCSS.getComputedStyle(button, null);
		assertEquals("red", style.getPropertyCSSValue("color").getCssText());
	}

	private static ViewCSS createViewCss(String css) throws IOException {
		CSSStyleSheet styleSheet = ParserTestUtil.parseCss(css);
		DocumentCSSImpl docCss = new DocumentCSSImpl();
		docCss.addStyleSheet(styleSheet);
		return new ViewCSSImpl(docCss);
	}


	@Test
	public void testBug261081() throws Exception{
		String css = "Button, Label { color: blue; font-weight: bold; }\n"
				+ "Button { color: black }\n";
		ViewCSS viewCSS = createViewCss(css);

		TestElement button = new TestElement("Button", engine);
		CSSStyleDeclaration style = viewCSS.getComputedStyle(button, null);
		assertEquals("black", style.getPropertyCSSValue("color").getCssText());
		assertEquals("bold", style.getPropertyCSSValue("font-weight").getCssText());
	}
}
