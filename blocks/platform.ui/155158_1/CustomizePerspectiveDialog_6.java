			}
		} else if (menuItem instanceof MDynamicMenuContribution) {
			IContributionItem contributionItem = menuMngrRenderer.getContribution(menuItem);
			dynamicEntry = new DynamicContributionItem(menuItem.getLocalizedLabel(), contributionItem);
			dynamicEntry.setImageDescriptor(getIconDescriptor(menuItem));
			dynamicEntry.setActionSet(idToActionSet.get(getActionSetID(contributionItem)));
			dynamicEntry.setCheckState(getMenuItemIsVisible(dynamicEntry));


			parent.addChild(dynamicEntry);
		} else if (menuItem instanceof MDirectMenuItem) {
			IContributionItem contributionItem = menuMngrRenderer.getContribution(menuItem);
			DisplayItem menuEntry = new DisplayItem(menuItem.getLocalizedLabel(), contributionItem);
			menuEntry.setImageDescriptor(getIconDescriptor(menuItem));
			menuEntry.setActionSet(idToActionSet.get(getActionSetID(menuItem)));
			menuEntry.setCheckState(getMenuItemIsVisible(menuEntry));
			parent.addChild(menuEntry);
		} else if (menuItem instanceof MHandledMenuItem) {
			IContributionItem contributionItem = menuMngrRenderer.getContribution(menuItem);

			MHandledMenuItem hmi = (MHandledMenuItem) menuItem;
			String text = hmi.getLocalizedLabel();
			if (text == null && hmi.getWbCommand() != null) {
				try {
					text = hmi.getWbCommand().getName();
				} catch (NotDefinedException e) {
