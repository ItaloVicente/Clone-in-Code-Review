				MMenu childModel = getMenuModel(childManager);
				if (childModel == null) {
					MMenu legacyModel = OpaqueElementUtil.createOpaqueMenu();
					legacyModel.setElementId(childManager.getId());
					legacyModel.setVisible(childManager.isVisible());
					legacyModel.setLabel(childManager.getMenuText());

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
						}
					} else {
						modelChildren.add(childModel);
					}
					if (childModel instanceof MPopupMenu) {
						if (((MPopupMenu) childModel).getContext() == null) {
							IEclipseContext lclContext = getContext(menuModel);
							if (lclContext != null) {
								((MPopupMenu) childModel).setContext(lclContext.createChild(childModel.getElementId()));
							}
						}
					}

					if (childModel.getChildren().size() != childManager.getSize()) {
						reconcileManagerToModel(childManager, childModel);
					}
				}
			} else if (item.isSeparator() || item.isGroupMarker()) {
				MMenuElement menuElement = getMenuElement(item);
				if (menuElement == null) {
					MMenuSeparator legacySep = OpaqueElementUtil.createOpaqueMenuSeparator();
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
							modelChildren.add(dest, legacySep);
						}
					} else {
						modelChildren.add(legacySep);
					}
