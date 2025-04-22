			if (window != null) {
				return window.isWorkbenchCoolItemId(id);
			}
			return false;
		}

		@Override
		protected void insertMenuGroup(IMenuManager menu, AbstractGroupMarker marker) {
			if (actionSetId != null) {
				IContributionItem[] items = menu.getItems();
				for (IContributionItem item : items) {
					if (item.isSeparator() || item.isGroupMarker()) {
						if (item instanceof IActionSetContributionItem) {
							String testId = ((IActionSetContributionItem) item).getActionSetId();
							if (actionSetId.compareTo(testId) < 0) {
								menu.insertBefore(item.getId(), marker);
								return;
							}
						}
					}
				}
			}

			menu.add(marker);
		}

		private IContributionItem findAlphabeticalOrder(String startId, String itemId, IContributionManager mgr) {
			IContributionItem[] items = mgr.getItems();
			int insertIndex = 0;

			while (insertIndex < items.length) {
				IContributionItem item = items[insertIndex];
				if (startId != null && startId.equals(item.getId())) {
