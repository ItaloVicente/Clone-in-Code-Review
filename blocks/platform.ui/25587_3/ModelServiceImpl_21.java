			MUIElement parent = curElement.getParent();
			if (parent instanceof MPerspective) {
				MElementContainer<MUIElement> perspectiveParent = parent.getParent();
				if (perspectiveParent == null) {
					return NOT_IN_UI;
				} else if (perspectiveParent.getSelectedElement() == parent) {
					return IN_ACTIVE_PERSPECTIVE;
