	private final String id;

	private final int storedHeight;

	private final int storedMinimumItems;

	private final boolean storedUseChevron;

	private final int storedWidth;

	PlaceholderContributionItem(final IToolBarContributionItem item) {
		item.saveWidgetState();
		id = item.getId();
		storedHeight = item.getCurrentHeight();
		storedWidth = item.getCurrentWidth();
		storedMinimumItems = item.getMinimumItemsToShow();
		storedUseChevron = item.getUseChevron();
	}

	@Override
