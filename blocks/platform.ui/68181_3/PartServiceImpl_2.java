		if (activePart != null) {
			if (activePerspective != null) {
				ContextFunction helper = (ContextFunction) activePerspective.getTransientData()
						.get(PERSPECTIVE_CHANGE_HELPER);
				if (helper != null) {
					return (MPart) helper.compute(activePerspective.getContext(), null);
				}
			}
		}
