		CSSStyleDeclaration styleDeclaration = getComputedStyle(getCombinedRules(), elt, pseudoElt);
		return styleDeclaration;
	}

	private List<CSSRule> getCombinedRules() {
		if (this.ruleCachingEnabled && this.currentCombinedRules != null) {
			return this.currentCombinedRules;
		}

