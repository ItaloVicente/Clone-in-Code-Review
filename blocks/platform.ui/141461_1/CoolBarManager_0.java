		for (IContributionItem item : items) {
			if (item.isSeparator()) {
				separatorFound = true;
			}
			if ((separatorFound) && (isChildVisible(item)) && (!item.isGroupMarker()) && (!item.isSeparator())) {
				numRows++;
				separatorFound = false;
			}
		}
