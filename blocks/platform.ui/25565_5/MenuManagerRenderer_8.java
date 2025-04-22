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
							}
						}

						if (childModel.getChildren().size() != childManager.getSize()) {
							reconcileManagerToModel(childManager, childModel);
