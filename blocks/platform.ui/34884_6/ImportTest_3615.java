package org.eclipse.e4.ui.tests.css.core.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.e4.ui.tests.css.core.util.ParserTestUtil;
import org.junit.Test;
import org.w3c.dom.css.CSSStyleSheet;

public class FontFaceRulesTest {

	@Test
	public void testEmptyFontFaceRule() throws Exception {
		String css = "@font-face {}\n"
				+ "Label { background-color: #FF0000 }";
		CSSStyleSheet styleSheet = ParserTestUtil.parseCss(css);
		assertNotNull(styleSheet);
		assertEquals(1, styleSheet.getCssRules().getLength());
	}

	@Test
	public void testFontFaceRuleWithProperties() throws Exception {
		String css = "@font-face {\n"
				+ "  font-family: \"Robson Celtic\";\n"
				+ "  src: url(\"http://site/fonts/rob-celt\")\n" + "}\n"
				+ "Label { background-color: #FF0000 }";
		CSSStyleSheet styleSheet = ParserTestUtil.parseCss(css);
		assertNotNull(styleSheet);
		assertEquals(1, styleSheet.getCssRules().getLength());
	}
}
