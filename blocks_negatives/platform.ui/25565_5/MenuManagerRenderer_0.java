		final ArrayList<MMenuContribution> toContribute = new ArrayList<MMenuContribution>();
		ContributionsAnalyzer.XXXgatherMenuContributions(menuModel,
				application.getMenuContributions(), elementId, toContribute,
				null, isPopup);
		generateContributions(menuModel, toContribute, isMenuBar);
		for (MMenuElement element : menuModel.getChildren()) {
			if (element instanceof MMenu) {
				processContributions((MMenu) element, element.getElementId(),
						false, isPopup);
