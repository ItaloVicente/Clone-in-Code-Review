		for (final DirectContributionItem dci : directItemsToCheck) {
			if (dci.getModel() != null && dci.getModel().getParent() != null && selector.select(dci.getModel())) {
				dci.updateItemEnablement();
			} else {
				directOrphanedToolItems.add(dci);
			}
		}
		if (!directOrphanedToolItems.isEmpty()) {
			directItemsToCheck.removeAll(directOrphanedToolItems);
			directOrphanedToolItems.clear();
		}
