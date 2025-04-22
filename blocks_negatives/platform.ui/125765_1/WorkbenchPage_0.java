		Collection<MPart> partsToHide = partService.getParts();
		List<MPart> partsOutsidePersp = modelService.findElements(window, null, MPart.class, null,
				EModelService.OUTSIDE_PERSPECTIVE);
		partsToHide.removeAll(partsOutsidePersp);

		for (MPart part : partsToHide) {
			hidePart(part, false, true, true);
		}

		MPerspectiveStack perspectiveStack = modelService.findElements(window, null,
				MPerspectiveStack.class, null).get(0);
		MPerspective current = perspectiveStack.getSelectedElement();
		for (Object perspective : perspectiveStack.getChildren().toArray()) {
			if (perspective != current) {
				modelService.removePerspectiveModel((MPerspective) perspective, window);
