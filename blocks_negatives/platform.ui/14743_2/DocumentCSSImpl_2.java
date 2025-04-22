			switch (rule.getType()) {
			case CSSRule.STYLE_RULE: {
				if (rule instanceof ExtendedCSSRule) {
					ExtendedCSSRule r = (ExtendedCSSRule) rule;
					SelectorList selectorList = r.getSelectorList();
					int l = selectorList.getLength();
					for (int j = 0; j < l; j++) {
						Selector selector = (Selector) selectorList.item(j);
						if (selector.getSelectorType() == selectorType) {
							switch (selectorType) {
							case Selector.SAC_CONDITIONAL_SELECTOR:
								ConditionalSelector conditionalSelector = (ConditionalSelector) selector;
								short conditionType = conditionalSelector
										.getCondition().getConditionType();
								if (selectorConditionType == conditionType) {
									list.add(selector);
								}
							}
