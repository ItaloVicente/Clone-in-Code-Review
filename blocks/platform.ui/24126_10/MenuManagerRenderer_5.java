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
