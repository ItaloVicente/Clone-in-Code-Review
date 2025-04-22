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
