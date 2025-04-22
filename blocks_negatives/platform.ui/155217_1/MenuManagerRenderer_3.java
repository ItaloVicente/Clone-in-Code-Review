						modelChildren.add(legacyItem);
					}
				} else if (OpaqueElementUtil.isOpaqueMenuItem(menuElement)) {
					MMenuItem legacyItem = (MMenuItem) menuElement;
					oldModelItems.remove(legacyItem);
					if (modelChildren.size() > dest) {
						if (modelChildren.get(dest) != legacyItem) {
							modelChildren.remove(legacyItem);
							modelChildren.add(dest, legacyItem);
						}
					} else {
						modelChildren.add(legacyItem);
