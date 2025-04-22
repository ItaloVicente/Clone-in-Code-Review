		MPart newActivePart = (MPart) changed;
		MUIElement partParent = newActivePart.getParent();
		if (partParent == null
				&& newActivePart.getCurSharedRef() != null)
			partParent = newActivePart.getCurSharedRef().getParent();

		while (partParent != null
				&& partParent instanceof MPartSashContainer)
			partParent = partParent.getParent();

		if (partParent instanceof MCompositePart) {
			partParent = partParent.getParent();
		}

		MPartStack pStack = (MPartStack) (partParent instanceof MPartStack ? partParent
				: null);

		List<String> tags = new ArrayList<String>();
		tags.add(CSSConstants.CSS_ACTIVE_CLASS);
		List<MUIElement> activeElements = modelService.findElements(
				modelService.getTopLevelWindowFor(newActivePart), null,
				MUIElement.class, tags);
		for (MUIElement element : activeElements) {
			if (element instanceof MPartStack && element != pStack) {
				styleElement(element, false);
			} else if (element instanceof MPart
					&& element != newActivePart) {
				styleElement(element, false);
			}
