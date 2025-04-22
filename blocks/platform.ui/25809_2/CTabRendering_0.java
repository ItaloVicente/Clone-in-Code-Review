		Control toolbarContainer = findToolbarContainer();

		if (toolbarContainer != null) {
			if (toolbarContainer.getBounds().height > 0) {
				int unselectedHeightToDraw = Math.max(
						toolbarContainer.getBounds().height,
						partHeaderBounds.height);

				rendererWrapper.drawBackground(gc, partHeaderBounds.x
						+ leftRightBorder, partHeaderBounds.height + topBorder,
						partHeaderBounds.width - leftRightBorder * 2,
						unselectedHeightToDraw, defaultBackground,
						getUnselectedTabsColors(state),
						getUnselectedTabsPercents(state), vertical);
			}
		}
	}
