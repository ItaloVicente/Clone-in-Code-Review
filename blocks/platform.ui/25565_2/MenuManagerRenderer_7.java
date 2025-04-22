						childModel.setVisible(childManager.isVisible());
						if (OpaqueElementUtil.isOpaqueMenu(childModel)) {
							oldMenus.remove(childModel);
						}
						if (modelChildren.size() > dest) {
							if (modelChildren.get(dest) != childModel) {
								modelChildren.remove(childModel);
								modelChildren.add(dest, childModel);
