		for (MPerspective mperspective : perspectives.getChildren()) {
			if (mperspective.getElementId().equals(perspective.getId())) {
				if (lastPerspective != null) {
					legacyWindow.firePerspectiveDeactivated(this, lastPerspective);
				}

				perspectives.setSelectedElement(mperspective);
				mperspective.getContext().activate();
				return;
