
			if (contributionItem instanceof SubContributionItem) {
				contributionItem = ((SubContributionItem) contributionItem).getInnerItem();
			}
			if (contributionItem instanceof SubMenuManager) {
				contributionItem = (IMenuManager) ((SubMenuManager) contributionItem).getParent();
			}

			if (processedOpaqueItems.containsKey(contributionItem)) {
				return null;
			}

			if (contributionItem.isDynamic()) {
				if (dynamicEntry == null || !contributionItem.equals(dynamicEntry.getIContributionItem())) {
					dynamicEntry = new DynamicContributionItem(contributionItem);
					dynamicEntry.setActionSet(idToActionSet.get(getActionSetID(contributionItem)));
					dynamicEntry.setCheckState(getMenuItemIsVisible(dynamicEntry));
					parent.addChild(dynamicEntry);
					processedOpaqueItems.put(contributionItem, dynamicEntry);
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
				processedOpaqueItems.put(contributionItem, menuEntry);
			} else if (contributionItem instanceof ActionContributionItem) {
