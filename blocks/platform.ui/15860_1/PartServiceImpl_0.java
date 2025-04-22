		MPart newActivePart = null;

		List<MPart> newPerspectiveParts = modelService.findElements(perspective, null, MPart.class,
				null);
		if (newPerspectiveParts.contains(activePart)
				&& partActivationHistory.isValid(perspective, activePart)) {
			newActivePart = activePart;
		} else if (newActivePart == null) {
			newActivePart = perspective.getContext().getActiveLeaf().get(MPart.class);
		}

		if (newActivePart == null) {
			modelService.bringToTop(perspective);
			perspective.getContext().activate();
		} else {
			if ((modelService.getElementLocation(newActivePart) & EModelService.IN_SHARED_AREA) != 0) {
				if (newActivePart.getParent().getSelectedElement() != newActivePart)
					newActivePart = (MPart) newActivePart.getParent().getSelectedElement();
