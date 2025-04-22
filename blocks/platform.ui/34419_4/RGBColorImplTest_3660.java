package org.eclipse.e4.ui.tests.css.core.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.e4.ui.tests.css.core.util.ParserTestUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

public class MediaRulesTest {

	@Test
	@Ignore("//THIS TEST KNOWN TO FAIL Dec 16/08")
	public void testMediaRule() throws Exception {
		String css = "@media screen, print {\n"
				+ "BODY { line-height: 1.2 }\n"
				+ "}\n" + "Label { background-color: #FF0000 }";
		CSSStyleSheet styleSheet = ParserTestUtil.parseCss(css);
		assertNotNull(styleSheet);
		CSSRuleList rules = styleSheet.getCssRules();


		assertEquals(1, rules.getLength());
	}
}
