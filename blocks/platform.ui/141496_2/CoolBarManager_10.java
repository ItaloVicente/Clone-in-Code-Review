			}

		}

		for (ListIterator<IContributionItem> iterator = displayedItems.listIterator(); iterator.hasNext();) {
			IContributionItem cbItem = iterator.next();
			if (cbItem.isSeparator()) {
				coolItemIndex = 0;
			} else {
				relocate(cbItem, coolItemIndex, contributionList, itemLocation);
				cbItem.saveWidgetState();
				coolItemIndex++;
			}
		}

		contributionList = adjustContributionList(contributionList);
		if (contributionList.size() != 0) {
			IContributionItem[] array = new IContributionItem[contributionList.size() - 1];
			array = contributionList.toArray(array);
			internalSetItems(array);
		}

	}

	private void relocate(IContributionItem cbItem, int index, ArrayList<IContributionItem> contributionList,
			HashMap<IContributionItem, Integer> itemLocation) {

		if ((itemLocation.get(cbItem) == null)) {
