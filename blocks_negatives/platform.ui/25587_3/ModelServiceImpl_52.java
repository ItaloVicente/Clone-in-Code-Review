			Object container = ((EObject) curElement).eContainer();
			if (!(container instanceof MUIElement))
				return NOT_IN_UI;

			if (container instanceof MApplication) {
				if (location != NOT_IN_UI)
					return location;
				return OUTSIDE_PERSPECTIVE;
			} else if (container instanceof MPerspective) {
				MPerspective perspective = (MPerspective) container;
				MUIElement perspParent = perspective.getParent();
				if (perspParent == null) {
					location = NOT_IN_UI;
				} else if (perspective.getParent().getSelectedElement() == perspective) {
					location |= IN_ACTIVE_PERSPECTIVE;
