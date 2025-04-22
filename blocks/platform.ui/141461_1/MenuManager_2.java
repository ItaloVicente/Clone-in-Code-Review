		for (IContributionItem childItem : childItems) {
			if (isChildVisible(childItem) && !childItem.isSeparator()) {
				visibleChildren = true;
				break;
			}
		}
