		if (minimizedElement.getTags().contains(IPresentationEngine.ORIENTATION_HORIZONTAL)) {
			horizontalItem.setSelection(true);
		} else if (minimizedElement.getTags().contains(IPresentationEngine.ORIENTATION_VERTICAL)) {
			verticalItem.setSelection(true);
		} else {
			defaultItem.setSelection(true);
