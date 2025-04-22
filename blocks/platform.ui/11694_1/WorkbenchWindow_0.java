	private void allowUpdates(IMenuManager menuManager) {
		menuManager.markDirty();
		final IContributionItem[] items = menuManager.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i] instanceof IMenuManager) {
				allowUpdates((IMenuManager) items[i]);
			} else if (items[i] instanceof SubContributionItem) {
				final IContributionItem innerItem = ((SubContributionItem) items[i]).getInnerItem();
				if (innerItem instanceof IMenuManager) {
					allowUpdates((IMenuManager) innerItem);
				}
			}
		}
	}

