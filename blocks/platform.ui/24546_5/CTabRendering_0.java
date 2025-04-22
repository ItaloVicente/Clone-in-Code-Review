		rendererWrapper.drawBackground(gc, partHeaderBounds.x,
				partHeaderBounds.y - 1, partHeaderBounds.width,
				partHeaderBounds.height, defaultBackground,
				getUnselectedTabsColors(state),
				getUnselectedTabsPercents(state),
				vertical);
	}

	private Color[] getUnselectedTabsColors(int state) {
