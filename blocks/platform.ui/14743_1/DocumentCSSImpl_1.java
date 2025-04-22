			if (rule.getType() == CSSRule.STYLE_RULE && rule instanceof ExtendedCSSRule) {
				ExtendedCSSRule r = (ExtendedCSSRule) rule;
				SelectorList selectorList = r.getSelectorList();
				int l = selectorList.getLength();
				for (int j = 0; j < l; j++) {
					Selector selector = (Selector) selectorList.item(j);
					if (selector.getSelectorType() == selectorType && selectorType == Selector.SAC_CONDITIONAL_SELECTOR) {
						ConditionalSelector conditionalSelector = (ConditionalSelector) selector;
						short conditionType = conditionalSelector.getCondition().getConditionType();
						if (selectorConditionType == conditionType) {
							list.add(selector);
