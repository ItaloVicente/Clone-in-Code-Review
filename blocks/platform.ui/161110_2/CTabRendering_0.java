
		boolean onBottom = parent.getTabPosition() == SWT.BOTTOM;
		int borderTop = onBottom ? INNER_KEYLINE + OUTER_KEYLINE : TOP_KEYLINE + OUTER_KEYLINE;
		Rectangle parentBounds = parent.getBounds();
		int y = (onBottom) ? 0 : partHeaderBounds.y + partHeaderBounds.height - 1;
		int height = (onBottom) ? parentBounds.height - partHeaderBounds.height + 2 * paddingTop + 2 * borderTop
				: parentBounds.height - partHeaderBounds.height;

		drawBackground(gc, partHeaderBounds.x, y, partHeaderBounds.width, height, defaultBackground, colors, percents,
				vertical);
