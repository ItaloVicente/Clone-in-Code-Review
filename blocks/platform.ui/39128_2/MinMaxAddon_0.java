	private boolean hasMinimizableChildren(MPartSashContainerElement elementToCheck) {
		if (MPartSashContainer.class.isInstance(elementToCheck)) {
			int partsToRender = 0;
			for (MPartSashContainerElement part : MPartSashContainer.class.cast(elementToCheck)
					.getChildren()) {

				boolean hasMinimizeableChild = hasMinimizableChildren(part);

				if (hasMinimizeableChild) {
					return true;
				}
				if (part.isToBeRendered() && part.isVisible())
					partsToRender++;
			}
			if (partsToRender > 1)
				return true;
		}
		return false;
	}

