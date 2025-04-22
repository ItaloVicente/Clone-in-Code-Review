		if (menu.getParent() != null) {
			IContributionManager manager = menuMngrRenderer.getManager(menu);
			if (manager != null) {
				IContributionItem[] items = manager.getItems();
				for (int i = 0; i < items.length; i++) {
					IContributionItem ci = items[i];
					if (ci.isDynamic()) {
						findDynamics.put(i > 0 ? items[i - 1] : null, ci);
					}
				}
				if (findDynamics.containsKey(null)) {
					IContributionItem item = findDynamics.get(null);
					dynamicEntry = new DynamicContributionItem(item);
					dynamicEntry.setCheckState(getMenuItemIsVisible(dynamicEntry));
					dynamicEntry.setActionSet(idToActionSet.get(getActionSetID(item)));
					parent.addChild(dynamicEntry);
				}
			}
		}

