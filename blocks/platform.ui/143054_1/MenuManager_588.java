		if (removeAllWhenShown) {
			return true;
		}

		IContributionItem[] childItems = getItems();
		boolean visibleChildren = false;
		for (IContributionItem childItem : childItems) {
			if (isChildVisible(childItem) && !childItem.isSeparator()) {
				visibleChildren = true;
				break;
			}
		}

		return visibleChildren;
	}

	@Override
