		boolean prevReconcileManagerToModel = isReconcileManagerToModel;
		isReconcileManagerToModel = true;
		try {
			List<MMenuElement> modelChildren = menuModel.getChildren();

			HashSet<MMenuItem> oldModelItems = new HashSet<MMenuItem>();
			HashSet<MMenu> oldMenus = new HashSet<MMenu>();
			HashSet<MMenuSeparator> oldSeps = new HashSet<MMenuSeparator>();
			for (MMenuElement itemModel : modelChildren) {
				if (OpaqueElementUtil.isOpaqueMenuSeparator(itemModel)) {
					oldSeps.add((MMenuSeparator) itemModel);
				} else if (OpaqueElementUtil.isOpaqueMenuItem(itemModel)) {
					oldModelItems.add((MMenuItem) itemModel);
				} else if (OpaqueElementUtil.isOpaqueMenu(itemModel)) {
					oldMenus.add((MMenu) itemModel);
				}
			}

			IContributionItem[] items = menuManager.getItems();
			for (int src = 0, dest = 0; src < items.length; src++, dest++) {
				IContributionItem item = items[src];
				if (item instanceof MenuManager) {
					MenuManager childManager = (MenuManager) item;
					MMenu childModel = getMenuModel(childManager);
					if (childModel == null) {
						MMenu legacyModel = OpaqueElementUtil
								.createOpaqueMenu();
						legacyModel.setElementId(childManager.getId());
						legacyModel.setVisible(childManager.isVisible());
						linkModelToManager(legacyModel, childManager);
						OpaqueElementUtil.setOpaqueItem(legacyModel,
								childManager);
						if (modelChildren.size() > dest) {
							modelChildren.add(dest, legacyModel);
						} else {
							modelChildren.add(legacyModel);
