			String label = menuItem.getLabel();
			String localizedLabel = menuItem.getLocalizedLabel();
			if ((label != null && label.length() != 0) || (localizedLabel != null && localizedLabel.length() != 0)
					|| (menuItem instanceof MHandledMenuItem) || menuItem.getWidget() != null) {
				IContributionItem contributionItem;
				if (menuItem instanceof MMenu) {
					contributionItem = menuMngrRenderer.getManager((MMenu) menuItem);
				} else {
					contributionItem = menuMngrRenderer.getContribution(menuItem);
