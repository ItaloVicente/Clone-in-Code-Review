package org.akrogen.tkui.css.core.dom.parsers;

import org.akrogen.tkui.css.core.resources.CSSCoreResources;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

public class CSSParserTest {

	public static void main(String[] args) {
		try {
			ICSSParserFactory factory = CSSParserFactory.newInstance();
			CSSParser cssParser = factory.makeCSSParser();

			InputSource styleSheetSource = new InputSource();
			styleSheetSource.setByteStream(CSSCoreResources.getHTMLSimple());

			CSSStyleSheet styleSheet = cssParser
					.parseStyleSheet(styleSheetSource);

			CSSRuleList ruleList = styleSheet.getCssRules();
			int length = ruleList.getLength();
			for (int i = 0; i < length; i++) {
				CSSRule rule = ruleList.item(i);
				System.out.println(rule);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
