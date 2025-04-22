		boolean current = element.isVisible();
		boolean visible = true;
		boolean evaluated = false;
		if (element.getPersistedState().get(VISIBILITY_IDENTIFIER) != null) {
			evaluated = true;
			String identifier = element.getPersistedState().get(
					VISIBILITY_IDENTIFIER);
			Object rc = evalContext.eclipseContext.get(identifier);
			if (rc instanceof Boolean) {
				visible = ((Boolean) rc).booleanValue();
			}
		}
		if (visible && (element.getVisibleWhen() instanceof MCoreExpression)) {
			evaluated = true;
			visible = ContributionsAnalyzer.isVisible(
					(MCoreExpression) element.getVisibleWhen(), evalContext);
