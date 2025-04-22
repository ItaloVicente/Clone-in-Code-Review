		for (MToolBarElement element : model.getChildren()) {
			if (element instanceof MToolBar) {
				clearModelToManager((MToolBar) element, getManager((MToolBar) element));
			}
			IContributionItem ici = getContribution(element);
			clearModelToContribution(element, ici);
		}
