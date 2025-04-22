	private void restoreEmptyPerspectiveStack(MUIElement element) {
		element.setVisible(true);
		element.getTags().remove(MINIMIZED_BY_ZOOM);

		MUIElement selectedElem = getWindowFor(element).getSelectedElement();
		if (selectedElem instanceof MPartSashContainer) {
			for (MUIElement elem : ((MPartSashContainer) selectedElem).getChildren()) {
				if (elem.getTags().contains(MAXIMIZED)) {
					elem.getTags().remove(MAXIMIZED);
				}
			}
		}
	}

