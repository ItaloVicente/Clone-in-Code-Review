		for (int i = 0; i < length; i++) {
			CSSRule rule = ruleList.get(i);
			if (rule.getType() == CSSRule.STYLE_RULE) {
				CSSStyleRule styleRule = (CSSStyleRule) rule;
				if (rule instanceof ExtendedCSSRule) {
					ExtendedCSSRule r = (ExtendedCSSRule) rule;
					SelectorList selectorList = r.getSelectorList();
					int l = selectorList.getLength();
					for (int j = 0; j < l; j++) {
						Selector selector = selectorList.item(j);
						if (selector instanceof ExtendedSelector) {
							ExtendedSelector extendedSelector = (ExtendedSelector) selector;
							if (extendedSelector.match(elt, pseudoElt)) {
								CSSStyleDeclaration style = styleRule
										.getStyle();
								int specificity = extendedSelector
										.getSpecificity();
								StyleWrapper wrapper = new StyleWrapper(style,
										specificity, position++);
								if (firstStyleDeclaration == null) {
									firstStyleDeclaration = wrapper;
								} else {
									if (styleDeclarations == null) {
										styleDeclarations = new ArrayList<>();
										styleDeclarations.add(firstStyleDeclaration);
									}
									styleDeclarations.add(wrapper);
								}
							}
