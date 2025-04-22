	private boolean hasMinimizableChildren(MPartSashContainerElement elementToCheck) {
		if (MPartSashContainer.class.isInstance(elementToCheck)) {
			int partsToRender = 0;
			for (MPartSashContainerElement part : MPartSashContainer.class.cast(elementToCheck)
					.getChildren()) {

				boolean hasMinimizeableChild = hasMinimizableChildren(part);

				if (hasMinimizeableChild) {
					return true;
				}
				if (isValidElement(part))
					partsToRender++;
			}
			if (partsToRender > 1)
				return true;
		}
		return false;
	}

	private boolean isValidElement(MUIElement part) {
		boolean validItem = part.isToBeRendered() && part.isVisible();
		if (MElementContainer.class.isInstance(part)) {
			validItem = false;
			for (Object element : MElementContainer.class.cast(part).getChildren()) {
				MUIElement innerElement = MUIElement.class.cast(element);
				validItem |= isValidElement(innerElement);
			}
		}
		return validItem;
	}

