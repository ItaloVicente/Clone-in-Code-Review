	private void hideNonRestorablePlaceholder(MPlaceholder placeholder) {
		MElementContainer<MUIElement> parent = placeholder.getParent();
		if (parent.getSelectedElement() == placeholder) {
			MUIElement candidate = null;
			for (MUIElement element : parent.getChildren()) {
				if (element.isVisible() && element.isToBeRendered() && element != placeholder) {
					candidate = element;
					break;
