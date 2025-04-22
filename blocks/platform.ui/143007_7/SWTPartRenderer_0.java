		return getImage(element, false);
	}

	protected Image getImage(MUILabel element, boolean imageChanged) {
		Image image = (Image) ((MUIElement) element).getTransientData()
				.get(IPresentationEngine.OVERRIDE_ICON_IMAGE_KEY);
