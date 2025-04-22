		if (isInContainer(part)) {
			MPlaceholder sharedRef = part.getCurSharedRef();
			MUIElement toBeRemoved = getRemoveTarget(part);
			MElementContainer<MUIElement> parent = getParent(toBeRemoved);
			List<MUIElement> children = parent.getChildren();

			if (toBeRemoved != part && toBeRemoved instanceof MPlaceholder
					&& sharedRef != toBeRemoved) {
				toBeRemoved.setToBeRendered(false);

				if (force || part.getTags().contains(REMOVE_ON_HIDE_TAG)) {
					parent.getChildren().remove(toBeRemoved);
				}
				return;
			}
