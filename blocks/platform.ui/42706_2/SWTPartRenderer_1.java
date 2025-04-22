
	public void processVisibility(IContributionItem ci, MUIElement itemModel) {
		if (itemModel.getVisibleWhen() instanceof MCoreExpression) {
			final IEclipseContext evalContext = modelService.getContainingContext(itemModel);
			ExpressionContext exprContext = new ExpressionContext(evalContext);
			boolean visible = ContributionsAnalyzer
					.isVisible((MCoreExpression) itemModel.getVisibleWhen(), exprContext);
			ci.setVisible(visible);
			itemModel.setVisible(visible);
		} else {
			ci.setVisible(itemModel.isVisible());
		}
	}

