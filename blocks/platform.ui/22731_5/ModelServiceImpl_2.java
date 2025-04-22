			if (container instanceof MApplication) {
				if (location != NOT_IN_UI)
					return location;
				return OUTSIDE_PERSPECTIVE;
			} else if (container instanceof MPerspective) {
				location = IN_ANY_PERSPECTIVE;
				MPerspective perspective = (MPerspective) container;
				if (perspective.getParent() != null
						&& perspective.getParent().getSelectedElement() == perspective)
					location |= IN_ACTIVE_PERSPECTIVE;
			} else if (container instanceof MTrimBar) {
				location = IN_TRIM;
			} else if (container instanceof MArea) {
				location = IN_SHARED_AREA;
