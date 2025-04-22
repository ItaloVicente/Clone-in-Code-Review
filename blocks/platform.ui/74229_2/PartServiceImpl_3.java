		if (parent.getSelectedElement() == toBeRemoved) {
			parent.setSelectedElement(null);
		}

		if (force || part.getTags().contains(REMOVE_ON_HIDE_TAG)) {
			children.remove(toBeRemoved);
