		if (lastContainer instanceof MPartStack) {
			return lastContainer;
		}

		MPartStack stack = modelService.createModelElement(MPartStack.class);
		stack.setElementId("CreatedByGetLastContainer"); //$NON-NLS-1$
		if (children.get(0) instanceof MPartSashContainer) {
			MPartSashContainer psc = (MPartSashContainer) children.get(0);
			psc.getChildren().add(stack);
		} else {
			modelService.insert(stack, (MPartSashContainerElement) children.get(0),
					EModelService.RIGHT_OF, 0.5f);
