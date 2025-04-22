	private DynamicContributionItem createMenuEntry(DisplayItem parent,
			Map<IContributionItem, IContributionItem> findDynamics, DynamicContributionItem dynamicEntry,
			MMenuElement menuItem) {
		String text = menuItem.getLocalizedLabel();
		if (text == null || text.length() == 0) {
			text = menuItem.getLabel();
