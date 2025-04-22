			if (OpaqueElementUtil.isOpaqueMenuSeparator(itemModel)) {
				oldSeps.add((MMenuSeparator) itemModel);
			} else if (OpaqueElementUtil.isOpaqueMenuItem(itemModel)) {
				oldModelItems.add((MMenuItem) itemModel);
			} else if (OpaqueElementUtil.isOpaqueMenu(itemModel)) {
				oldMenus.add((MMenu) itemModel);
