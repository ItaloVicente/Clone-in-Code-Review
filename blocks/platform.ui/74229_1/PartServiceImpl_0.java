		if (!isInContainer(part)) {
			return;
		}

		MPlaceholder sharedRef = part.getCurSharedRef();
		MUIElement toBeRemoved = getRemoveTarget(part);
		MElementContainer<MUIElement> parent = getParent(toBeRemoved);
		List<MUIElement> children = parent.getChildren();

		if (toBeRemoved != part && toBeRemoved instanceof MPlaceholder && sharedRef != toBeRemoved) {
			toBeRemoved.setToBeRendered(false);
