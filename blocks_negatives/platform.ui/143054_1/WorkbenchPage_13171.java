		MPerspectiveStack perspectiveStack = modelService.findElements(window, null,
				MPerspectiveStack.class, null).get(0);
		MPerspective current = perspectiveStack.getSelectedElement();
		for (Object perspective : perspectiveStack.getChildren().toArray()) {
			if (perspective != current) {
				modelService.removePerspectiveModel((MPerspective) perspective, window);
