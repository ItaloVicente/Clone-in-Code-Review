			ExpressionContext eContext = new ExpressionContext(workbenchWindow.getModel()
					.getContext());
			ContributionsAnalyzer.gatherMenuContributions(menuModel,
					application.getMenuContributions(), location, toContribute, eContext, true);
			ContributionsAnalyzer.addMenuContributions(menuModel, toContribute,
					menuContributionsToRemove);
