			MUIElement parent = curElement.getParent();
			if (parent instanceof MPerspective) {
				MElementContainer<MUIElement> perspectiveParent = parent.getParent();
				if (perspectiveParent == null) {
					return NOT_IN_UI;
				} else if (perspectiveParent.getSelectedElement() == parent) {
					return IN_ACTIVE_PERSPECTIVE;
				} else {
					return IN_ANY_PERSPECTIVE;
				}
			} else if (parent instanceof MApplication) {
				return OUTSIDE_PERSPECTIVE;
			} else if (parent instanceof MTrimBar) {
				return IN_TRIM;
			} else if (parent == null) {
				EObject container = ((EObject) curElement).eContainer();

				if (container instanceof MWindow) {
					MWindow containerWin = (MWindow) container;
					if (containerWin.getSharedElements().contains(curElement)) {
						return IN_SHARED_AREA;
					}
