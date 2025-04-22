	private static boolean shouldReactToChildVisibilityChanges(MUIElement theElement) {
		return (theElement instanceof MPartSashContainer || theElement instanceof MPartStack
				|| theElement instanceof MCompositePart)
				&& !theElement.getTags().contains(IPresentationEngine.HIDDEN_EXPLICITLY);
	}

