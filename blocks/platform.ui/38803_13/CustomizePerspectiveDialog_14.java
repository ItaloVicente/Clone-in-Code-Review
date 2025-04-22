			if (findDynamics.containsKey(contributionItem)) {
				IContributionItem item = findDynamics.get(contributionItem);
				dynamicEntry = new DynamicContributionItem(item);
				dynamicEntry.setCheckState(getMenuItemIsVisible(dynamicEntry));
				dynamicEntry.setActionSet(idToActionSet.get(getActionSetID(contributionItem)));
				parent.addChild(dynamicEntry);
			} else {
				return dynamicEntry;
			}
		} else if (OpaqueElementUtil.isOpaqueMenuItem(menuItem)) {
			IContributionItem contributionItem = menuMngrRenderer.getContribution(menuItem);
			if (contributionItem instanceof ActionContributionItem) {
				final IAction action = ((ActionContributionItem) contributionItem).getAction();
				DisplayItem menuEntry = new DisplayItem(action.getText(), contributionItem);
				menuEntry.setImageDescriptor(action.getImageDescriptor());
				menuEntry.setActionSet(idToActionSet.get(getActionSetID(contributionItem)));
				parent.addChild(menuEntry);
				if (menuEntry.getChildren().isEmpty()) {
					menuEntry.setCheckState(getMenuItemIsVisible(menuEntry));
				}
			}
		} else {
			return dynamicEntry;
