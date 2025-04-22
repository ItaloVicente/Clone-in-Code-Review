		String text = menuItem.getLocalizedLabel();
		if (text == null || text.length() == 0) {
			text = menuItem.getLabel();
		}
		if ((text != null && text.length() != 0) || (menuItem instanceof MHandledMenuItem)
				|| menuItem.getWidget() != null) {
			IContributionItem contributionItem;
			if (menuItem instanceof MMenu) {
				contributionItem = menuMngrRenderer.getManager((MMenu) menuItem);
