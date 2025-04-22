	protected List<Selector> querySelector(CSSRuleList ruleList, int selectorType, int selectorConditionType) {
		List<Selector> list = new ArrayList<Selector>();
		if (selectorType == Selector.SAC_CONDITIONAL_SELECTOR) {
			int length = ruleList.getLength();
			for (int i = 0; i < length; i++) {
				CSSRule rule = ruleList.item(i);
				if (rule.getType() == CSSRule.STYLE_RULE && rule instanceof ExtendedCSSRule) {
