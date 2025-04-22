		for (CSSRule rule : ruleList) {
			if (rule.getType() != CSSRule.STYLE_RULE || (!(rule instanceof ExtendedCSSRule)) ) {
				continue; // we only handle the CSSRule.STYLE_RULE and ExtendedCSSRule case
			}
			CSSStyleRule styleRule = (CSSStyleRule) rule;
			ExtendedCSSRule r = (ExtendedCSSRule) rule;
			SelectorList selectorList = r.getSelectorList();
			int l = selectorList.getLength();
			for (int j = 0; j < l; j++) {
				Selector selector = selectorList.item(j);
				if (selector instanceof ExtendedSelector) {
					ExtendedSelector extendedSelector = (ExtendedSelector) selector;
					if (extendedSelector.match(elt, pseudoElt)) {
						CSSStyleDeclaration style = styleRule.getStyle();
						int specificity = extendedSelector.getSpecificity();
						StyleWrapper wrapper = new StyleWrapper(style, specificity, position++);
						if (firstStyleDeclaration == null) {
							firstStyleDeclaration = wrapper;
