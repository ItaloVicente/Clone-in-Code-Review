
	public void updateContributionItems(Selector selector) {
		for (final HandledContributionItem hci : itemsToCheck) {
			if (hci.model != null && hci.model.getParent() != null
					&& selector.select(hci.model)) {
				hci.updateItemEnablement();
			} else {
				orphanedToolItems.add(hci);
			}
		}
		if (!orphanedToolItems.isEmpty()) {
			itemsToCheck.removeAll(orphanedToolItems);
			orphanedToolItems.clear();
		}

	}
