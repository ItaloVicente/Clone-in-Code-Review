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
									((MPopupMenu) childModel)
											.setContext(lclContext
													.createChild(childModel
															.getElementId()));
								}
