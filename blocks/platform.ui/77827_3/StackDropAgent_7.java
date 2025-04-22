	private Rectangle getCenterBounds(CTabFolder ctf) {
		Rectangle centerBounds = ctf.getBounds();
		int heightSplitRegion = Math.min(centerBounds.height / 4, 64);
		int widthSplitRegion = Math.min(centerBounds.width / 4, 64);
		Geometry.expand(centerBounds, -widthSplitRegion, -widthSplitRegion, -heightSplitRegion, -heightSplitRegion);
		centerBounds = Display.getCurrent().map(ctf.getParent(), null, centerBounds);
		return centerBounds;
	}

