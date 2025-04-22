				} else if (item instanceof SubMenuManager) {
					menuManager.remove(items[src]);
				} else {
					MMenuElement menuElement = getMenuElement(item);
					if (menuElement == null) {
						MMenuItem legacyItem = OpaqueElementUtil
								.createOpaqueMenuItem();
						legacyItem.setElementId(item.getId());
						legacyItem.setVisible(item.isVisible());
						OpaqueElementUtil.setOpaqueItem(legacyItem, item);
						linkModelToContribution(legacyItem, item);
						if (modelChildren.size() > dest) {
