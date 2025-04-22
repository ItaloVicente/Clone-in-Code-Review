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
