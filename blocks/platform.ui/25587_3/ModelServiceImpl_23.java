					EObject containerParent = container.eContainer();
					if (containerParent instanceof MPerspective) {
						MElementContainer<MUIElement> perspectiveParent = ((MPerspective) containerParent)
								.getParent();
						if (perspectiveParent == null) {
							return NOT_IN_UI;
						}
						int location = IN_ANY_PERSPECTIVE;
						if (perspectiveParent.getSelectedElement() == containerParent) {
							location |= IN_ACTIVE_PERSPECTIVE;
						}
						return location;
					} else if (containerParent instanceof MWindow) {
						return OUTSIDE_PERSPECTIVE;
					} else {
						return NOT_IN_UI;
					}
				}
			}
			curElement = parent;
