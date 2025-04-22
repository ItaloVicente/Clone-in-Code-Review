			for (MPart part : partsToHide) {
				hidePart(part, false, true, true);
			}
			MPerspectiveStack perspectiveStack = modelService.findElements(window, null, MPerspectiveStack.class)
					.get(0);
			MPerspective current = perspectiveStack.getSelectedElement();
			for (Object perspective : perspectiveStack.getChildren().toArray()) {
				if (perspective != current) {
					modelService.removePerspectiveModel((MPerspective) perspective, window);
				}
			}
