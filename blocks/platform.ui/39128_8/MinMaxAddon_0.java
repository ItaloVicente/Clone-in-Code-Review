	private boolean hasMinimizableChildren(MPartSashContainerElement elementToCheck) {
		if (elementToCheck instanceof MPartSashContainer) {
			int partsToRender = 0;
			for (MPartSashContainerElement part : ((MPartSashContainer) elementToCheck)
					.getChildren()) {

				boolean hasMinimizeableChild = hasMinimizableChildren(part);

				if (hasMinimizeableChild) {
					return true;
				}
				if (hasVisibleContent(part))
					partsToRender++;
			}
			if (partsToRender > 1)
				return true;
		}
		return false;
	}

	private boolean hasVisibleContent(MUIElement part) {
		boolean validItem = part.isToBeRendered() && part.isVisible();
		if (part instanceof MElementContainer) {
			validItem = false;
			for (Object element : ((MElementContainer<?>) part).getChildren()) {
				MUIElement innerElement = (MUIElement) element;
				validItem |= hasVisibleContent(innerElement);
			}
		}
		return validItem;
	}

