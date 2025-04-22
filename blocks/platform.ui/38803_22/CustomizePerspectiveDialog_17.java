		if (text == null) {
			text = getToolbarLabel(element);
		}

		DisplayItem toolBarEntry = new DisplayItem(text, contributionItem);
		if (iconDescriptor != null) {
			toolBarEntry.setImageDescriptor(iconDescriptor);
		}
		toolBarEntry.setActionSet(idToActionSet.get(getActionSetID(element)));
		if (toolBarEntry.getChildren().isEmpty()) {
			toolBarEntry.setCheckState(getToolbarItemIsVisible(toolBarEntry));
		}
		parent.addChild(toolBarEntry);
	}

	private static boolean isGroupOrSeparator(MToolBarElement element, IContributionItem contributionItem) {
		return element instanceof MToolBarSeparator
				|| (contributionItem == null || contributionItem.isGroupMarker() || contributionItem
						.isSeparator());
