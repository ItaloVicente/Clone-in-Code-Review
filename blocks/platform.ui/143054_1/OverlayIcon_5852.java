	public OverlayIcon(ImageDescriptor base, ImageDescriptor[][] overlays,
			Point size) {
		fBase = base;
		fOverlays = overlays;
		fSize = size;
	}

	protected void drawBottomLeft(ImageDescriptor[] overlays) {
		if (overlays == null) {
