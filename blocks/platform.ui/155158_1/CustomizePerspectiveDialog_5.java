
			if (contributionItem instanceof SubContributionItem) {
				contributionItem = ((SubContributionItem) contributionItem).getInnerItem();
			}

			if (contributionItem.isDynamic()) {
				if (dynamicEntry == null || !contributionItem.equals(dynamicEntry.getIContributionItem())) {
					dynamicEntry = new DynamicContributionItem(contributionItem);
					dynamicEntry.setActionSet(idToActionSet.get(getActionSetID(contributionItem)));
					dynamicEntry.setCheckState(getMenuItemIsVisible(dynamicEntry));
					parent.addChild(dynamicEntry);
				}

				if (menuItem.getWidget() != null) {
					dynamicEntry.addCurrentItem((MenuItem) menuItem.getWidget());
				}

				return dynamicEntry;
			} else if (contributionItem instanceof CommandContributionItem) {
				CommandContributionItem cci = (CommandContributionItem) contributionItem;
				CommandContributionItemParameter data = cci.getData();
				DisplayItem menuEntry = new DisplayItem(data.label, contributionItem);
				menuEntry.setImageDescriptor(data.icon);
				menuEntry.setActionSet(idToActionSet.get(getActionSetID(contributionItem)));
				menuEntry.setCheckState(getMenuItemIsVisible(menuEntry));
				parent.addChild(menuEntry);
			} else if (contributionItem instanceof ActionContributionItem) {
