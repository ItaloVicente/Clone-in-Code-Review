		if (!coolBarExist()) {
			return false;
		}
		return coolBar.getLocked();
	}

	private int getNumRows(IContributionItem[] items) {
		int numRows = 1;
		boolean separatorFound = false;
		for (int i = 0; i < items.length; i++) {
			if (items[i].isSeparator()) {
				separatorFound = true;
			}
			if ((separatorFound) && (isChildVisible(items[i])) && (!items[i].isGroupMarker())
					&& (!items[i].isSeparator())) {
				numRows++;
				separatorFound = false;
			}
		}
		return numRows;
	}

	@Override
