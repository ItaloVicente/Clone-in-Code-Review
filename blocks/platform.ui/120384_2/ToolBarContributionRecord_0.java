		if (toolbarModel.getVisibleWhen() instanceof MCoreExpression) {
			MCoreExpression visibleWhen = (MCoreExpression) toolbarModel.getVisibleWhen();
			boolean currentVisibility = ContributionsAnalyzer.isVisible(visibleWhen,
					exprContext);
			if (toolbarModel.isVisible() != currentVisibility) {
				toolbarModel.setVisible(currentVisibility);
			}
		}
