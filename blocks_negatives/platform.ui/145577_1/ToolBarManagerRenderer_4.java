				MToolItem legacyItem = OpaqueElementUtil.createOpaqueToolItem();
				legacyItem.setElementId(item.getId());
				legacyItem.setVisible(item.isVisible());
				OpaqueElementUtil.setOpaqueItem(legacyItem, item);
				linkModelToContribution(legacyItem, item);
				modelChildren.add(dest, legacyItem);
			} else if (OpaqueElementUtil.isOpaqueToolItem(element)) {
				MToolItem legacyItem = (MToolItem) element;
				oldModelItems.remove(legacyItem);
				if (modelChildren.size() > dest) {
					if (modelChildren.get(dest) != legacyItem) {
						modelChildren.remove(legacyItem);
						modelChildren.add(dest, legacyItem);
					}
				} else {
					modelChildren.add(legacyItem);
				}
