				}
			} else if (item.isSeparator() || item.isGroupMarker()) {
				MMenuElement menuElement = getMenuElement(item);
				if (menuElement == null) {
					MMenuSeparator legacySep = OpaqueElementUtil
							.createOpaqueMenuSeparator();
					legacySep.setElementId(item.getId());
					legacySep.setVisible(item.isVisible());
					OpaqueElementUtil.setOpaqueItem(legacySep, item);
					linkModelToContribution(legacySep, item);
					if (modelChildren.size() > dest) {
						modelChildren.add(dest, legacySep);
					} else {
						modelChildren.add(legacySep);
					}
				} else if (OpaqueElementUtil.isOpaqueMenuSeparator(menuElement)) {
					MMenuSeparator legacySep = (MMenuSeparator) menuElement;
					oldSeps.remove(legacySep);
					if (modelChildren.size() > dest) {
						if (modelChildren.get(dest) != legacySep) {
							modelChildren.remove(legacySep);
