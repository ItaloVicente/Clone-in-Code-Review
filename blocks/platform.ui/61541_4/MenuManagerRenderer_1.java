			clearModelToContribution(mMenuElement, ici);
		}
	}

	@SuppressWarnings("unchecked")
	public void removeDynamicMenuContributions(MenuManager menuManager, MMenu menuModel) {
		for (MMenuElement menuElement : new HashSet<>(modelToContribution.keySet())) {
			if (menuElement instanceof MDynamicMenuContribution) {
				final IContributionItem contributionItem = modelToContribution.get(menuElement);

				if (contributionItem instanceof DynamicContributionContributionItem) {
					final DynamicContributionContributionItem dynamicContributionItem = (DynamicContributionContributionItem) contributionItem;

					if ((dynamicContributionItem.getParent() instanceof MenuManager)
							&& dynamicContributionItem.getParent().equals(menuManager)) {
						final ArrayList<MMenuElement> childElements = (ArrayList<MMenuElement>) menuElement
								.getTransientData().get(MenuManagerShowProcessor.DYNAMIC_ELEMENT_STORAGE_KEY);

						if (childElements != null) {
							removeDynamicMenuContributions(menuManager, menuModel, childElements);
						}
					}
				}
			}
