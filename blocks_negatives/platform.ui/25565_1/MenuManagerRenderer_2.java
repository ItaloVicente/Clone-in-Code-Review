		IContributionItem[] items = menuManager.getItems();
		for (int src = 0, dest = 0; src < items.length; src++, dest++) {
			IContributionItem item = items[src];
			if (item instanceof MenuManager) {
				MenuManager childManager = (MenuManager) item;
				MMenu childModel = getMenuModel(childManager);
				if (childModel == null) {
					MMenu legacyModel = OpaqueElementUtil.createOpaqueMenu();
					legacyModel.setElementId(childManager.getId());
					legacyModel.setVisible(childManager.isVisible());
					linkModelToManager(legacyModel, childManager);
					OpaqueElementUtil.setOpaqueItem(legacyModel, childManager);
					if (modelChildren.size() > dest) {
						modelChildren.add(dest, legacyModel);
					} else {
						modelChildren.add(legacyModel);
					}
					reconcileManagerToModel(childManager, legacyModel);
				} else {
					if (OpaqueElementUtil.isOpaqueMenu(childModel)) {
						oldMenus.remove(childModel);
					}
					if (modelChildren.size() > dest) {
						if (modelChildren.get(dest) != childModel) {
							modelChildren.remove(childModel);
							modelChildren.add(dest, childModel);
