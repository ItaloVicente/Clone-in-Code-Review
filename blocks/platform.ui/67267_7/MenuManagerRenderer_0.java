		if (model instanceof MMenu) {
			for (MMenuElement element : ((MMenu) model).getChildren()) {
				IContributionItem ici = getContribution(element);
				clearModelToContribution(element, ici);
			}
		}
