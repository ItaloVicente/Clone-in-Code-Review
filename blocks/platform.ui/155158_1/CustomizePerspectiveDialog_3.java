			parent.addChild(menuEntry);
		} else if (RenderedElementUtil.isRenderedMenuItem(menuItem)) {
			IContributionItem contributionItem = menuMngrRenderer.getContribution(menuItem);

			if (dynamicEntry == null || !contributionItem.equals(dynamicEntry.getIContributionItem())) {
				dynamicEntry = new DynamicContributionItem(contributionItem);
