		MPart newActivePart = (MPart) changed;
		MUIElement partParent = newActivePart.getParent();
		if (partParent == null && newActivePart.getCurSharedRef() != null)
			partParent = newActivePart.getCurSharedRef().getParent();

		while (partParent != null && partParent instanceof MPartSashContainer)
			partParent = partParent.getParent();

		if (partParent instanceof MCompositePart) {
			partParent = partParent.getParent();
		}
