
		MUIElement hasSizeData = dragElement;
		while (hasSizeData != null
				&& (MUIElement) hasSizeData.getParent() instanceof MPartSashContainer == false) {
			hasSizeData = hasSizeData.getParent();
		}

		MElementContainer<MUIElement> originalParent = dragElement.getParent();

