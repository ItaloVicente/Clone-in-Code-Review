					break;
				}
				if (cbItem.isSeparator()) {
					break;
				}
			}
			nextRow(rowIterator, false);
			numRow++;
		}

		Iterator<Integer> existingRows = existingVisibleRows.iterator();
		if (existingRows.hasNext()) {
			row = existingRows.next().intValue();
		}

		HashMap<IContributionItem, Integer> itemLocation = new HashMap<>();
		for (ListIterator<IContributionItem> locationIterator = displayedItems.listIterator(); locationIterator
				.hasNext();) {
			IContributionItem item = locationIterator.next();
			if (item.isSeparator()) {
				if (existingRows.hasNext()) {
					Integer value = existingRows.next();
					row = value.intValue();
				} else {
					row++;
				}
			} else {
