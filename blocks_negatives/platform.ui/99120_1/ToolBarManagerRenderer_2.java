		if (model instanceof MToolBar) {
			for (MToolBarElement element : ((MToolBar) model).getChildren()) {
				IContributionItem ici = getContribution(element);
				clearModelToContribution(element, ici);
			}
		}
