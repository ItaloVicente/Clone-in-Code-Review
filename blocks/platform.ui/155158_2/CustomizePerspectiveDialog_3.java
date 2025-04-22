
			if (menuEntry.getChildren().isEmpty()) {
				menuEntry.setCheckState(getMenuItemIsVisible(menuEntry));
			}
		} else if (RenderedElementUtil.isRenderedMenuItem(menuItem)) {
			IContributionItem contributionItem = menuMngrRenderer.getContribution(menuItem);

			if (dynamicEntry == null || !contributionItem.equals(dynamicEntry.getIContributionItem())) {
				dynamicEntry = new DynamicContributionItem(contributionItem);
