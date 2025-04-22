		ArrayList<IContributionItem> contributionList = getItemList();

		if (contributionList.isEmpty()) {
			return;
		}

		CoolItem[] coolItems = coolBar.getItems();
		int[] wrapIndicies = getAdjustedWrapIndices(coolBar.getWrapIndices());

		int row = 0;
		int coolItemIndex = 0;

		ArrayList<IContributionItem> displayedItems = new ArrayList<>(coolBar.getItemCount());
		for (int i = 0; i < coolItems.length; i++) {
			CoolItem coolItem = coolItems[i];
			if (coolItem.getData() instanceof IContributionItem) {
				IContributionItem cbItem = (IContributionItem) coolItem.getData();
				displayedItems.add(Math.min(i, displayedItems.size()), cbItem);
			}
		}

		int offset = 0;
		for (int i = 1; i < wrapIndicies.length; i++) {
			int insertAt = wrapIndicies[i] + offset;
			displayedItems.add(insertAt, new Separator(USER_SEPARATOR));
			offset++;
		}

		ArrayList<Integer> existingVisibleRows = new ArrayList<>(4);
		ListIterator<IContributionItem> rowIterator = contributionList.listIterator();
		collapseSeparators(rowIterator);
		int numRow = 0;
		while (rowIterator.hasNext()) {
			while (rowIterator.hasNext()) {
				IContributionItem cbItem = rowIterator.next();
				if (displayedItems.contains(cbItem)) {
