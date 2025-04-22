		} else if (RenderedElementUtil.isRenderedMenuItem(menuItem)) {
			IContributionItem contributionItem = menuMngrRenderer.getContribution(menuItem);

			if (dynamicEntry == null || !contributionItem.equals(dynamicEntry.getIContributionItem())) {
				dynamicEntry = new DynamicContributionItem(contributionItem);
				dynamicEntry.setActionSet(idToActionSet.get(getActionSetID(menuItem)));
