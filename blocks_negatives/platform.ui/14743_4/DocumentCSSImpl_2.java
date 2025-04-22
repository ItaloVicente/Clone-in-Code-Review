	protected List querySelector(CSSRuleList ruleList, int selectorType,
			int selectorConditionType) {
		List list = new ArrayList();
		int length = ruleList.getLength();
		for (int i = 0; i < length; i++) {
			CSSRule rule = ruleList.item(i);
			switch (rule.getType()) {
			case CSSRule.STYLE_RULE: {
				if (rule instanceof ExtendedCSSRule) {
