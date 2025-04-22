		Assert.isNotNull(item);
		super.itemRemoved(item);
		CoolItem coolItem = findCoolItem(item);
		if (coolItem != null) {
			coolItem.setData(null);
		}
	}

	private void nextRow(ListIterator<IContributionItem> iterator, boolean ignoreCurrentItem) {

		IContributionItem currentElement = null;
		if (!ignoreCurrentItem && iterator.hasPrevious()) {
			currentElement = iterator.previous();
			iterator.next();
		}

		if ((currentElement != null) && (currentElement.isSeparator())) {
			collapseSeparators(iterator);
