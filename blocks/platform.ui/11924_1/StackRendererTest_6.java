
	private CTabItem getTabItem(MPart part) {
		for (CTabItem tabItem : ((CTabFolder) partStack.getWidget()).getItems()) {
			if (part.getLabel().equals(tabItem.getText())) {
				return tabItem;
			}
		}

		throw new IllegalArgumentException(
				"CTabItem don't found for given part item: " + part);
	}
