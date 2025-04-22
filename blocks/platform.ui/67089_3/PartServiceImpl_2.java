	private boolean isMinimized(MUIElement elt) {
		List<String> tags = elt.getTags();
		return (tags.contains(IPresentationEngine.MINIMIZED)
				|| tags.contains(IPresentationEngine.MINIMIZED_BY_ZOOM))
				&& !tags.contains(IPresentationEngine.ACTIVE);
	}

